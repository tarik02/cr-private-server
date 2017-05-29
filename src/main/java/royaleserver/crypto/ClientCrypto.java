package royaleserver.crypto;

import royaleserver.protocol.Info;
import royaleserver.protocol.MessageHeader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class ClientCrypto extends Crypto {
	protected ServerCrypto server;

	public ClientCrypto(byte[] serverKey) {
		super();

		TweetNaCl.crypto_sign_keypair(privateKey, clientKey, false);
		this.serverKey = serverKey;

		sharedKey = Curve25519.scalarMult(privateKey, serverKey);
		sharedKey = Salsa.HSalsa20(new byte[16], sharedKey, Salsa.SIGMA);

		encryptNonce = new Nonce();
	}

	public void setServer(ServerCrypto server) {
		this.server = server;
	}

	@Override
	public void decryptPacket(MessageHeader message) {
		switch (message.id) {
		case Info.SERVER_HELLO:
		case Info.LOGIN_FAILED:
			int len = ByteBuffer.wrap(message.payload, 0, 4).order(ByteOrder.LITTLE_ENDIAN).getInt(0);
			sessionKey = Arrays.copyOfRange(message.payload, 4, 4 + len);
			message.decrypted = message.payload;
			break;
		case Info.LOGIN_OK:
			Nonce nonce = new Nonce(clientKey, serverKey, encryptNonce.getBytes());
			message.decrypted = decrypt(message.payload, nonce);

			if (message.decrypted != null) {
				try {
					decryptNonce = new Nonce(Arrays.copyOfRange(message.decrypted, 0, 24));
					server.encryptNonce = new Nonce(Arrays.copyOfRange(message.decrypted, 0, 24));
				} catch (Exception e) {
					e.printStackTrace();
				}

				sharedKey = Arrays.copyOfRange(message.decrypted, 24, 56);

				message.decrypted = Arrays.copyOfRange(message.decrypted, 56, message.decrypted.length);
			}
			break;
		default:
			message.decrypted = decrypt(message.payload);
		}
	}

	@Override
	public void encryptPacket(MessageHeader message) {
		switch (message.id) {
		case Info.CLIENT_HELLO:
			message.payload = message.decrypted;
			break;
		case Info.LOGIN:
			Nonce nonce = new Nonce(clientKey, serverKey);
			ByteArrayOutputStream toEncrypt = new ByteArrayOutputStream();

			try {
				toEncrypt.write(sessionKey);
				toEncrypt.write(encryptNonce.getBytes());
				toEncrypt.write(message.decrypted);
			} catch (IOException ignored) {}

			ByteArrayOutputStream encrypted = new ByteArrayOutputStream();

			try {
				encrypted.write(clientKey);
				encrypted.write(encrypt(toEncrypt.toByteArray(), nonce));
			} catch (IOException ignored) {}

			message.payload = encrypted.toByteArray();
			break;
		default:
			message.payload = encrypt(message.decrypted);
		}
	}
}
