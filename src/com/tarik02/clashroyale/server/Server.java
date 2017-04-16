package com.tarik02.clashroyale.server;

import com.tarik02.clashroyale.server.crypto.ClientCrypto;
import com.tarik02.clashroyale.server.crypto.ServerCrypto;
import com.tarik02.clashroyale.server.protocol.MessageHeader;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.protocol.messages.MessageFactory;
import com.tarik02.clashroyale.server.utils.Hex;
import com.tarik02.clashroyale.server.utils.LogManager;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.logging.Logger;

public class Server {
	private static Logger logger = LogManager.getLogger(Server.class);

	protected boolean running = false;
	protected long tickCounter = 0;

	protected ServerSocket serverSocket = null;
	protected NetworkThread networkThread = null;

	public Server() {
		start();
	}

	public void start() {
		if (running) {
			return;
		}
		running = true;
		tickCounter = 0;

		logger.info("Starting the server...");

		logger.info("Starting the network thread...");
		networkThread = new NetworkThread();
		networkThread.start();

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

		serverSocket = null;
		networkThread = null;

		logger.info("Server stopped!");
	}

	protected void tick() {

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

	private class ClientThread extends Thread {
		private Socket socket;
		private Player player;

		public ClientThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			player = new Player(Server.this, socket);

			byte[] serverKey = Hex.toByteArray("9e6657f2b419c237f6aeef37088690a642010586a7bd9018a15652bab8370f4f");
			ClientCrypto clientCrypto = new ClientCrypto(serverKey);
			ServerCrypto serverCrypto = new ServerCrypto();

			clientCrypto.setServer(serverCrypto);
			serverCrypto.setClient(clientCrypto);

			try {
				DataInputStream reader = new DataInputStream(socket.getInputStream());

				while (true) {
					byte[] payload = new byte[2];
					reader.readFully(payload);
					short id = ByteBuffer
						.allocate(2)
						.put(payload)
						.order(ByteOrder.BIG_ENDIAN).getShort(0);

					payload = new byte[3];
					reader.readFully(payload);

					int length = ByteBuffer
						.allocate(4)
						.put((byte)0)
						.put(payload)
						.order(ByteOrder.BIG_ENDIAN).getInt(0);
					payload = new byte[length];
					reader.readFully(payload);

					MessageHeader header = new MessageHeader(id, payload);
					serverCrypto.decryptPacket(header);

					//logger.info("" + header.id);
					//logger.info(Hex.dump(header.decrypted));

					Message message = MessageFactory.create(header.id);

					logger.info("");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			player.close();

			socket = null;
			player = null;
		}
	}
}
