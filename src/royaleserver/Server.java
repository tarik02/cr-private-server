package royaleserver;

import com.google.gson.Gson;
import royaleserver.config.Config;
import royaleserver.crypto.ClientCrypto;
import royaleserver.crypto.ServerCrypto;
import royaleserver.protocol.Info;
import royaleserver.protocol.MessageHeader;
import royaleserver.protocol.Session;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.MessageFactory;
import royaleserver.protocol.messages.client.ClientHello;
import royaleserver.protocol.messages.client.Login;
import royaleserver.protocol.messages.server.*;
import royaleserver.utils.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import royaleserver.csv.Table;
import royaleserver.database.DataManager;
import royaleserver.database.provider.DataProvider;
import royaleserver.database.provider.SQLDataProvider;

public class Server {
	private static Logger logger;

	protected File workingDirectory;

	protected boolean running = false;
	protected long tickCounter = 0;

	protected ServerSocket serverSocket = null;
	protected NetworkThread networkThread = null;
	protected DataManager dataManager = null;

	protected String resourceFingerprint = "";

	protected Config config;

	public Server() throws ServerException {
		this(null);
	}

	public Server(File workingDirectory) throws ServerException {
		if (workingDirectory == null) {
			workingDirectory = new File(".");
		}
		this.workingDirectory = workingDirectory;

		if (!workingDirectory.exists() || !workingDirectory.isDirectory()) {
			throw new ServerException("The working directory is not exists.");
		}

		LogManager.initMainLogger(new File(workingDirectory, "server.log"));
		logger = LogManager.getLogger(Server.class);

		resourceFingerprint = new String(getResource("fingerprint.json"), Charset.forName("UTF-8"));
		start();
	}

	public void start() throws ServerException {
		if (running) {
			return;
		}
		running = true;
		tickCounter = 0;

		logger.info("Starting the server...");

		logger.info("Reading config...");
		try {
			config = (new Gson()).fromJson(new InputStreamReader(new FileInputStream(new File(workingDirectory, "config.json"))), Config.class);
		} catch (Throwable e) {
			logger.error("Cannot read config.", e);
			throw new ServerException("Cannot read config.");
		}

		logger.info("Starting the network thread...");
		networkThread = new NetworkThread();
		networkThread.start();
		
		logger.info("Initializing data manager...");
		DataProvider dataProvider = null;
		switch (config.database.provider) {
		case "mysql":
			dataProvider = new SQLDataProvider((new StringBuilder())
				.append("jdbc:mysql://")
				.append(config.database.mysql.host)
				.append(":")
				.append(config.database.mysql.port)
				.append("/")
				.append(config.database.mysql.database)
				.toString(),
				config.database.mysql.user,
				config.database.mysql.password);
			break;
		case "sqlite":
			dataProvider = new SQLDataProvider((new StringBuilder())
				.append("jdbc:sqlite:").append(config.database.sqlite.database).toString(), null, null);
			break;
		}
		if (dataProvider == null) {
			throw new ServerException("Failed to initialize data provider.");
		}
		dataManager = new DataManager(dataProvider);
		dataManager.update(DataManager.DATABASE_VERSION);

		logger.info("Server started!");

		while (running) {
			final long startTime = System.currentTimeMillis();
			tick();
			++tickCounter;
			final long endTime = System.currentTimeMillis();
			final long diff = endTime - startTime;

			if (diff < 50) {
				try {
					Thread.sleep(50 - diff);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void stop() {
		if (!running) {
			return;
		}
		running = false;

		logger.info("Stopping the server...");
		logger.info("Disconnecting the clients...");
		// TODO: Disconnect clients

		try {
			logger.info("Closing the server socket...");
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			logger.info("Joining the network thread...");
			networkThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.info("Stopping data manager...");
		dataManager.stop();

		serverSocket = null;
		networkThread = null;
		dataManager = null;

		logger.info("Server stopped!");
	}

	protected void tick() {
		dataManager.handleTasks();
	}

	public byte[] getResource(String path) throws ServerException {
		File file = new File(workingDirectory, "assets/" + path);
		if (!file.exists()) {
			throw new ServerException("Resource does not exists.");
		}

		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			return buffer;
		} catch (IOException ignored) {
			throw new ServerException("Failed to fetch the resource.");
		}
	}

	public Table getCSVResource(String path) throws ServerException {
		return new Table(getResource(path));
	}

	public String getResourceFingerprint() {
		return resourceFingerprint;
	}

	private class NetworkThread extends Thread {
		public void run() {
			try {
				serverSocket = new ServerSocket(9339);

				while (running) {
					Socket clientSocket = serverSocket.accept();
					(new ClientThread(clientSocket)).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class ClientThread extends Thread implements Session {
		private Socket socket;
		private DataInputStream reader;
		private DataOutputStream writer;

		private ClientCrypto clientCrypto;
		private ServerCrypto serverCrypto;

		private Player player;

		public ClientThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			byte[] serverKey = Hex.toByteArray("9e6657f2b419c237f6aeef37088690a642010586a7bd9018a15652bab8370f4f");
			byte[] sessionKey = Hex.toByteArray("74794DE40D62A03AC6F6E86A9815C6262AA12BEDD518F883");
			clientCrypto = new ClientCrypto(serverKey);
			serverCrypto = new ServerCrypto();

			clientCrypto.setServer(serverCrypto);
			serverCrypto.setClient(clientCrypto);

loop:
			try {
				reader = new DataInputStream(socket.getInputStream());
				writer = new DataOutputStream(socket.getOutputStream());

				Message message = readMessage();
				if (message.id != Info.CLIENT_HELLO) {
					logger.warn("Excepted ClientHello, received %s. Disconnecting...", message.getClass().getSimpleName());
					break loop;
				}

				{
					ClientHello clientHello = (ClientHello)message;
					message = null;
				}

				{
					ServerHello serverHello = new ServerHello();
					serverHello.sessionKey = sessionKey;
					writeMessage(serverHello);
				}

				message = readMessage();
				if (message.id != Info.LOGIN) {
					logger.warn("Excepted Login, received %s. Disconnecting...", message.getClass().getSimpleName());
					break loop;
				}

				{
					Login login = (Login)message;
					/*if (login.resourceSha.equals("65dded3fcfd249dec11f4db84c05a4b37cd7a427")) {
						LoginFailed loginFailed = new LoginFailed();
						loginFailed.errorCode = 7;
						loginFailed.resourceFingerprintData = resourceFingerprint;
						loginFailed.redirectDomain = "";
						loginFailed.contentURL = "http://7166046b142482e67b30-2a63f4436c967aa7d355061bd0d924a1.r65.cf1.rackcdn.com";
						loginFailed.updateURL = "";
						loginFailed.reason = "";
						loginFailed.secondsUntilMaintenanceEnd = 0;
						loginFailed.unknown_7 = (byte)0;
						loginFailed.unknown_8 = "";
						writeMessage(loginFailed);
						break loop;
					}*/
					message = null;

					LoginOk loginOk = new LoginOk();
					loginOk.userId = loginOk.homeId = 1515; // TODO: Get it from store
					loginOk.userToken = "8zn8t2bjy8cnk26re8899c3mhc9xa7pg7tb4yk3m"; // TODO: Get it from store
					loginOk.gameCenterId = "";
					loginOk.facebookId = "";
					loginOk.serverMajorVersion = 3; // TODO: Make it constant
					loginOk.serverBuild = 193; // TODO: Make it constant
					loginOk.contentVersion = 8; // TODO: Make it constant
					loginOk.environment = "prod";
					loginOk.sessionCount = 5;
					loginOk.playTimeSeconds = 114; // TODO: Get it from store
					loginOk.daysSinceStartedPlaying = 0; // TODO: Get it from store
					loginOk.facebookAppId = "1475268786112433";
					loginOk.serverTime = String.valueOf(System.currentTimeMillis());
					loginOk.accountCreatedDate = String.valueOf(System.currentTimeMillis() - 50000); // TODO: Get it from store
					loginOk.unknown_16 = 0;
					loginOk.googleServiceId = "";
					loginOk.unknown_18 = "";
					loginOk.unknown_19 = "";
					loginOk.region = "UA"; // TODO: Make it from config
					loginOk.contentURL = "http://7166046b142482e67b30-2a63f4436c967aa7d355061bd0d924a1.r65.cf1.rackcdn.com"; // TODO: Make it from config
					loginOk.eventAssetsURL = "https://event-assets.clashroyale.com"; // TODO: Make it from config
					loginOk.unknown_23 = 1;
					writeMessage(loginOk);

					player = new Player(login.accountId, Server.this, this);
					player.sendOwnHomeData();
				}

				logger.info("Player connected.");

				while (true) {
					message = readMessage();
					if (message != null) {
						try {
							if (!message.handle(player)) {
								logger.warn("Failed to handle message %s:\n%s", message.getClass().getSimpleName(), Dumper.dump(message));
							}
						} catch (Throwable e) {
							logger.error("Failed to handle message %s:\n%s. Error throwed:", e, message.getClass().getSimpleName(), Dumper.dump(message));
						}
					}

					message = null;
				}
			} catch (EOFException ignored) {

			} catch (Exception e) {
				logger.error("Error while looping client.", e);
			}

			try {
				socket.close();
			} catch (IOException e) {
				logger.error("Failed to close the socket.", e);
			}

			if (player != null) {
				player.close();
			}

			socket = null;
			reader = null;

			clientCrypto = null;
			serverCrypto = null;

			player = null;
		}

		public Message readMessage() throws IOException {
			byte[] payload = new byte[2];
			reader.readFully(payload);
			short id = ByteBuffer
				.allocate(2)
				.put(payload)
				.order(ByteOrder.BIG_ENDIAN).getShort(0);

			payload = new byte[3];
			reader.readFully(payload);
			reader.readShort(); // Version, always 5

			int length = ByteBuffer
				.allocate(4)
				.put((byte)0)
				.put(payload)
				.order(ByteOrder.BIG_ENDIAN).getInt(0);
			payload = new byte[length];
			reader.readFully(payload);

			MessageHeader header = new MessageHeader(id, payload);
			serverCrypto.decryptPacket(header);

			Message message = MessageFactory.create(header.id);

			if (message == null) {
				if (header.decrypted == null) {
					logger.error("Failed to decrypt packet %d, encrypted payload:\n%s", header.id, Hex.dump(header.payload));
				} else {
					String name = null;
					if (Info.messagesMap.containsKey(header.id)) {
						name = Info.messagesMap.get(header.id);
					}

					if (name == null) {
						logger.warn("Received unknown packet %d:\n%s", header.id, Hex.dump(header.decrypted));
						/*if (header.id == 10099) {
							player.disconnect("DEBUGGING"); // Sometimes I receive this packet
						}*/
					} else {
						logger.warn("Received undefined packet %s:\n%s", name, Hex.dump(header.decrypted));
					}
				}
			} else if (header.decrypted == null) {
				logger.error("Failed to decrypt packet %s, encrypted payload:\n%s", message.getClass().getSimpleName(), Hex.dump(header.payload));
			} else {
				logger.debug("> %s", message.getClass().getSimpleName());

				try {
					message.decode(new DataStream(header.decrypted));
				} catch (Exception e) {
					logger.error("Failed to decode packet %s, payload:\n%s", message.getClass().getSimpleName(), Hex.dump(header.decrypted));
				}

				return message;
			}

			return null;
		}

		public void writeMessage(Message message) throws IOException {
			logger.debug("< %s", message.getClass().getSimpleName());

			DataStream stream = new DataStream();
			message.encode(stream);
			MessageHeader header = new MessageHeader();
			header.id = message.id;
			header.decrypted = stream.getBuffer();
			serverCrypto.encryptPacket(header);

			writer.write(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(message.id).array());
			writer.write(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(header.payload.length).array(), 1, 3);
			writer.write(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort((short)5).array());
			writer.write(header.payload);
		}


		@Override
		public void sendMessage(Message message) {
			try {
				writeMessage(message);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void close() {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static class ServerException extends Exception {
		public ServerException(String message) {
			super(message);
		}
	}
}
