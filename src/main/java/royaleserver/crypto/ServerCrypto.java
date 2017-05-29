package royaleserver.crypto;

import royaleserver.protocol.Info;
import royaleserver.protocol.MessageHeader;
import royaleserver.utils.Hex;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ServerCrypto extends Crypto {
	protected ClientCrypto client;

	public ServerCrypto() {
		privateKey = Hex.toByteArray("1891d401fadb51d25d3a9174d472a9f691a45b974285d47729c45c6538070d85");
		serverKey = Hex.toByteArray("72f1a4a4c48e44da0c42310f800e96624e6dc6a641a9d41c3b5039d8dfadc27e");
	}

	public void setClient(ClientCrypto client) {
		this.client = client;
		sharedKey = client.sharedKey;
	}

	@Override
	public void decryptPacket(MessageHeader message) {
		switch (message.id) {
		case Info.CLIENT_HELLO:
			message.decrypted = message.payload;
			break;
		case Info.LOGIN:
			clientKey = Arrays.copyOfRange(message.payload, 0, 32);
			byte[] cipherText = Arrays.copyOfRange(message.payload, 32, message.payload.length);

			sharedKey = Curve25519.scalarMult(privateKey, clientKey);
			sharedKey = Salsa.HSalsa20(new byte[16], sharedKey, Salsa.SIGMA);

			Nonce nonce = new Nonce(clientKey, serverKey);
			message.decrypted = decrypt(cipherText, nonce);

			if (message.decrypted != null) {
				sessionKey = Arrays.copyOfRange(message.decrypted, 0, 24);
				try {
					decryptNonce = new Nonce(Arrays.copyOfRange(message.decrypted, 24, 48));
					client.encryptNonce = new Nonce(Arrays.copyOfRange(message.decrypted, 24, 48));
				} catch (Exception e) {
					e.printStackTrace();
				}

				message.decrypted = Arrays.copyOfRange(message.decrypted, 48, message.decrypted.length);
			}
			break;
		default:
			message.decrypted = decrypt(message.payload);
		}
	}

	@Override
	public void encryptPacket(MessageHeader message) {
		switch (message.id) {
		case Info.SERVER_HELLO:
		case Info.LOGIN_FAILED:
			message.payload = message.decrypted;
			break;
		case Info.LOGIN_OK:
			Nonce nonce = new Nonce(clientKey, serverKey, decryptNonce.getBytes());
			ByteArrayOutputStream toEncrypt = new ByteArrayOutputStream();

			try {
				toEncrypt.write(encryptNonce.getBytes());
				toEncrypt.write(sharedKey);
				toEncrypt.write(message.decrypted);
			} catch (IOException ignored) {}

			message.payload = encrypt(toEncrypt.toByteArray(), nonce);
			break;
		default:
			message.payload = encrypt(message.decrypted);
		}
	}
}
