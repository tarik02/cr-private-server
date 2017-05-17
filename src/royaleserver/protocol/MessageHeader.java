package royaleserver.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageHeader {
	public short id;
	public byte[] payload;
	public byte[] decrypted;

	public MessageHeader() {}

	public MessageHeader(short id, byte[] bytes) {
		this.id = id;
		payload = bytes;
	}

	public byte[] toBuffer() {
		return payload;
	}
}
