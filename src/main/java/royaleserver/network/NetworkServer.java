package royaleserver.network;

import java.nio.*;
import org.jboss.netty.buffer.*;
import org.jboss.netty.channel.*;
import org.jboss.netty.handler.codec.replay.*;
import royaleserver.*;
import royaleserver.crypto.*;
import royaleserver.network.protocol.*;
import royaleserver.network.protocol.client.*;
import royaleserver.network.protocol.client.messages.*;
import royaleserver.network.protocol.server.*;
import royaleserver.network.protocol.server.messages.*;
import royaleserver.utils.*;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.database.service.PlayerService;

public final class NetworkServer {
	private static final Logger logger = LogManager.getLogger(NetworkServer.class);
	private static final byte[] serverKey = Hex.toByteArray("9e6657f2b419c237f6aeef37088690a642010586a7bd9018a15652bab8370f4f");
	private static final byte[] sessionKey = Hex.toByteArray("74794DE40D62A03AC6F6E86A9815C6262AA12BEDD518F883");

	private final Server server;
	private OrderedMemoryAwareThreadPoolExecutor bossExec;
	private OrderedMemoryAwareThreadPoolExecutor ioExec;
	private ServerBootstrap networkServer;
	private Channel channel;

	public NetworkServer(Server server) {
		this.server = server;

		ClientCommandFactory.instance.init();
		ClientMessageFactory.instance.init();
		ServerCommandFactory.instance.init();
		ServerMessageFactory.instance.init();
	}

	public void start() {
		int workingThreadsCount = 4;
		bossExec = new OrderedMemoryAwareThreadPoolExecutor(1, 400000000, 2000000000, 60, TimeUnit.SECONDS);
		ioExec = new OrderedMemoryAwareThreadPoolExecutor(workingThreadsCount, 400000000, 2000000000, 60, TimeUnit.SECONDS);
		networkServer = new ServerBootstrap(new NioServerSocketChannelFactory(bossExec, ioExec, workingThreadsCount));
		networkServer.setOption("backlog", 500);
		networkServer.setOption("connectTimeoutMillis", 10000);
		networkServer.setPipelineFactory(new ServerPipelineFactory());
		channel = networkServer.bind(new InetSocketAddress(9339));
	}

	public void stop() {
		try {
			channel.unbind().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			channel.close().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private class ServerPipelineFactory implements ChannelPipelineFactory {
		@Override
		public ChannelPipeline getPipeline() throws Exception {
			ClientCrypto clientCrypto = new ClientCrypto(serverKey);
			ServerCrypto serverCrypto = new ServerCrypto();

			clientCrypto.setServer(serverCrypto);
			serverCrypto.setClient(clientCrypto);

			PacketFrameDecoder decoder = new PacketFrameDecoder(serverCrypto);
			PacketFrameEncoder encoder = new PacketFrameEncoder(serverCrypto);
			return Channels.pipeline(decoder, encoder, new PlayerHandler(NetworkServer.this.server));
		}
	}

	private static class PlayerHandler extends SimpleChannelUpstreamHandler implements NetworkSession {
		private enum Status {
			HELLO,
			LOGIN,
			CONNECTED,
			DISCONNECTING
		}

		private final Server server;
		private Player player;

		private Channel channel;
		private ChannelFuture lastWrite = null;
		private Status status;

		public PlayerHandler(Server server) {
			this.server = server;
			status = Status.HELLO;
		}

		@Override
		public void channelConnected(ChannelHandlerContext context, ChannelStateEvent e) throws Exception {
			channel = context.getChannel();
		}

		@Override
		public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
			if (player != null) {
				player.close("", false);
			}
		}

		@Override
		public void messageReceived(ChannelHandlerContext context, MessageEvent e) {
			if (e.getChannel().isOpen()) {
				ClientMessage message = (ClientMessage)e.getMessage();
				logger.debug("> %s", message.getClass().getSimpleName());

				switch (status) {
				case HELLO:
					if (message.id != Messages.CLIENT_HELLO) {
						logger.warn("Excepted ClientHello, received %s. Disconnecting...", message.getClass().getSimpleName());
						close();
						return;
					}

					ClientHello clientHello = (ClientHello)message;

					ServerHello serverHello = new ServerHello();
					serverHello.sessionKey = sessionKey;
					sendMessage(serverHello);

					status = Status.LOGIN;
					break;
				case LOGIN:
					if (message.id != Messages.LOGIN) {
						logger.warn("Excepted Login, received %s. Disconnecting...", message.getClass().getSimpleName());
						close();
						return;
					}

					Login login = (Login)message;
					/*if (login.resourceSha.equals("863227dfdea3a47d55da528a39c6123d17c961be")) {
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

					PlayerService playerService = server.getDataManager().getPlayerService();
					PlayerEntity playerEntity;

					if (login.accountId == 0 && login.passToken.isEmpty()) {
						playerEntity = playerService.create();
					} else {
						playerEntity = playerService.get(login.accountId);

						if (playerEntity == null || !login.passToken.equals(playerEntity.getPassToken())) {
							LoginFailed loginFailed = new LoginFailed();
							loginFailed.errorCode = LoginFailed.ERROR_CODE_RESET_ACCOUNT;
							loginFailed.resourceFingerprintData = "";
							loginFailed.redirectDomain = "";
							loginFailed.contentURL = "http://7166046b142482e67b30-2a63f4436c967aa7d355061bd0d924a1.r65.cf1.rackcdn.com";
							loginFailed.updateURL = "https://play.google.com/store/apps/details?id=com.supercell.clashroyale";
							loginFailed.reason = "";
							loginFailed.secondsUntilMaintenanceEnd = -64;
							loginFailed.unknown_7 = (byte)0;
							loginFailed.unknown_8 = "";
							sendMessage(loginFailed);
							return;
						}
					}

					LoginOk loginOk = new LoginOk();
					loginOk.userId = loginOk.homeId = playerEntity.getId();
					loginOk.userToken = playerEntity.getPassToken();
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
					sendMessage(loginOk);

					player = new Player(playerEntity, server, this);
					player.sendOwnHomeData();

					status = Status.CONNECTED;
					break;
				case CONNECTED:
					try {
						if (!message.handle(player)) {
							logger.warn("Failed to handle message %s:\n%s", message.getClass().getSimpleName(), Dumper.dump(message));
						}
					} catch (Throwable e2) {
						logger.error("Failed to handle message %s:\n%s. Error throwed:", e2, message.getClass().getSimpleName(), Dumper.dump(message));
					}
					break;
				case DISCONNECTING:

					break;
				}
			}
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
			logger.error("Exception from downstream", e.getCause());
			ctx.getChannel().close();
		}

		@Override
		public void sendMessage(Message message) {
			logger.debug("< %s", message.getClass().getSimpleName());
			lastWrite = channel.write(message);
		}

		@Override
		public void close() {
			status = Status.DISCONNECTING;

			if (lastWrite != null) {
				lastWrite.addListener(ChannelFutureListener.CLOSE);
			} else {
				channel.close();
			}
		}
	}

	private static class PacketFrameEncoder extends OneToOneEncoder {
		private final ServerCrypto crypto;

		public PacketFrameEncoder(ServerCrypto crypto) {
			this.crypto = crypto;
		}

		@Override
		protected Object encode(ChannelHandlerContext channelHandlerContext, Channel channel, Object object) throws Exception {
			ServerMessage message = (ServerMessage)object;

			DataStream stream = new DataStream();
			message.encode(stream);
			MessageHeader header = new MessageHeader();
			header.id = message.id;
			header.decrypted = stream.getBuffer();
			crypto.encryptPacket(header);

			stream.reset(true);

			stream.put(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(message.id).array());
			stream.put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(header.payload.length).array(), 1, 3);
			stream.put(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort((short)5).array());
			stream.put(header.payload);

			return ChannelBuffers.wrappedBuffer(stream.getBuffer());
		}
	}

	private static class PacketFrameDecoder extends ReplayingDecoder<VoidEnum> {
		private final ServerCrypto crypto;

		public PacketFrameDecoder(ServerCrypto crypto) {
			this.crypto = crypto;
		}

		@Override
		public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
			ctx.sendUpstream(e);
		}
		@Override
		public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
			ctx.sendUpstream(e);
		}
		@Override
		protected Object decode(ChannelHandlerContext channelHandlerContext, Channel arg1, ChannelBuffer buffer, VoidEnum ignored) throws Exception {
			byte[] payload = new byte[2];
			buffer.readBytes(payload);
			short id = ByteBuffer
					.allocate(2)
					.put(payload)
					.order(ByteOrder.BIG_ENDIAN).getShort(0);

			payload = new byte[3];
			buffer.readBytes(payload);
			buffer.readShort(); // Version, always 5

			int length = ByteBuffer
					.allocate(4)
					.put((byte)0)
					.put(payload)
					.order(ByteOrder.BIG_ENDIAN).getInt(0);
			payload = new byte[length];
			buffer.readBytes(payload);

			MessageHeader header = new MessageHeader(id, payload);
			crypto.decryptPacket(header);

			ClientMessage message = ClientMessageFactory.instance.create(header.id);

			if (header.decrypted == null) {
				logger.error("Failed to decrypt packet %d, encrypted payload:\n%s", header.id, Hex.dump(header.payload));
				return null;
			}

			if (message == null) {
				String name = null;
				if (Messages.messagesMap.containsKey(header.id)) {
					name = Messages.messagesMap.get(header.id);
				}

				if (name == null) {
					logger.warn("Received unknown packet %d:\n%s", header.id, Hex.dump(header.decrypted));
				} else {
					logger.warn("Received undefined packet %s:\n%s", name, Hex.dump(header.decrypted));
				}

				return null;
			}

			try {
				message.decode(new DataStream(header.decrypted));
			} catch (Exception e) {
				logger.error("Failed to decode packet %s, payload:\n%s", message.getClass().getSimpleName(), Hex.dump(header.decrypted));
				return null;
			}

			return message;
		}
	}
}