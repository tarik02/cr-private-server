package royaleserver.crypto;

import royaleserver.protocol.MessageHeader;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

public abstract class Crypto {
	public static class CryptoException extends Exception {
		public CryptoException(String message) {
			super(message);
		}
	}

	private static Logger logger = LogManager.getLogger(Crypto.class);

	protected byte[] privateKey = new byte[TweetNaCl.SIGN_PUBLIC_KEY_BYTES];
	protected byte[] serverKey;
	protected byte[] clientKey = new byte[TweetNaCl.SIGN_SECRET_KEY_BYTES];
	protected byte[] sharedKey;
	protected Nonce decryptNonce = new Nonce();
	protected Nonce encryptNonce = new Nonce();
	protected byte[] sessionKey;

	public byte[] getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(byte[] sessionKey) {
		this.sessionKey = sessionKey;
	}

	public byte[] getSharedKey() {
		return sharedKey;
	}

	public void setSharedKey(byte[] sharedKey) throws CryptoException {
		if (sharedKey.length != 32) {
			throw new CryptoException("sharedKey.length must be 32");
		}

		this.sharedKey = sharedKey;
	}

	public byte[] encrypt(byte[] message) {
		return encrypt(message, null);
	}

	public byte[] encrypt(byte[] message, Nonce nonce) {
		if (nonce == null) {
			encryptNonce.increment();
			nonce = encryptNonce;
		}

		return TweetNaCl.secretbox(message, nonce.getBytes(), sharedKey);
	}

	public byte[] decrypt(byte[] message) {
		return decrypt(message, null);
	}

	public byte[] decrypt(byte[] message, Nonce nonce) {
		if (nonce == null) {
			decryptNonce.increment();
			nonce = decryptNonce;
		}

		return TweetNaCl.secretbox_open(message, nonce.getBytes(), sharedKey);
	}

	public abstract void decryptPacket(MessageHeader message);
	public abstract void encryptPacket(MessageHeader message);
}
