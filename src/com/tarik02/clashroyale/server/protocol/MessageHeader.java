package com.tarik02.clashroyale.server.protocol;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MessageHeader {
	public short id;
	public short version;
	public byte[] payload;
	public byte[] decrypted;

	public MessageHeader() {}

	public MessageHeader(short id, byte[] bytes) {
		this.id = id;

		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		buffer.order(ByteOrder.BIG_ENDIAN);

		version = buffer.getShort(0);
		payload = new byte[bytes.length - 2];
		buffer.get(payload, 0, payload.length);
	}

	public byte[] toBuffer() {
		return ByteBuffer.allocate(2 + payload.length).order(ByteOrder.BIG_ENDIAN)
			.putShort(version)
			.put(payload)
			.array();
	}
}
