package com.tarik02.clashroyale.server.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class DataStream {
	protected byte[] buffer;
	protected int offset, count;

	public DataStream() {
		this(new byte[32]);
	}

	public DataStream(byte[] buffer) {
		this(buffer, 0);
	}

	public DataStream(byte[] buffer, int offset) {
		this.buffer = buffer;
		this.offset = offset;
		count = 0;
	}

	public void reset() {
		buffer = new byte[32];
		offset = 0;
		count = 0;
	}

	public byte[] getBuffer() {
		return Arrays.copyOf(buffer, count);
	}

	public void setBuffer(byte[] buffer) {
		if (buffer == null) {
			this.buffer = new byte[32];
			count = 0;
		} else {
			this.buffer = buffer;
			count = buffer.length;
		}
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		ensureCapacity(offset);
		this.offset = offset;
	}

	public int getCount() {
		return count;
	}

	protected void ensureCapacity(int capacity) {
		if (buffer.length < capacity) {
			buffer = Arrays.copyOf(buffer, buffer.length << 1);
		}
	}

	public byte[] get() {
		byte[] result = Arrays.copyOfRange(buffer, offset, count);
		offset = count;
		return result;
	}

	public byte[] get(int len) {
		len = Math.min(len, count - offset);

		byte[] result = Arrays.copyOfRange(buffer, offset, offset + len);
		offset += len;

		return result;
	}

	public DataStream put(byte[] bytes) {
		return put(bytes, 0, bytes.length);
	}

	public DataStream put(byte[] bytes, int start, int length) {
		length = Math.min(length, bytes.length);

		ensureCapacity(count + length);
		System.arraycopy(bytes, start, buffer, count, length);
		count += length;

		return this;
	}

	public boolean getBoolean() {
		return getByte() == 0x01;
	}

	public DataStream putBoolean(boolean value) {
		return putByte(value ? (byte)0x01 : (byte)0x00);
	}

	public byte getByte() {
		if (offset < count) {
			return buffer[offset];
		}

		return 0;
	}

	public DataStream putByte(byte value) {
		ensureCapacity(count + 1);
		buffer[count] = value;
		++count;
		return this;
	}

	public short getBShort() {
		return ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).put(get(2)).getShort(0);
	}

	public short getLShort() {
		return ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).put(get(2)).getShort(0);
	}

	public int getBTriad() {
		return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).put((byte)0x00).put(get(3)).getInt(0);
	}

	public int getLTriad() {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).put(get(3)).put((byte)0x00).getInt(0);
	}

	public int getBInt() {
		return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).put(get(4)).getInt(0);
	}

	public int getLInt() {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).put(get(4)).getInt(0);
	}

	public long getBLong() {
		return ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).put(get(8)).getLong(0);
	}

	public long getLLong() {
		return ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).put(get(8)).getLong(0);
	}

	public float getBFloat() {
		return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).put(get(4)).getFloat(0);
	}

	public float getLFloat() {
		return ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).put(get(4)).getFloat(0);
	}

	public DataStream putBShort(short value) {
		return put(ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN).putShort(value).array());
	}

	public DataStream putLShort(short value) {
		return put(ByteBuffer.allocate(2).order(ByteOrder.LITTLE_ENDIAN).putShort(value).array());
	}

	public DataStream putBTriad(int value) {
		return put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(value).array(), 1, 3);
	}

	public DataStream putLTriad(int value) {
		return put(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array(), 0, 3);
	}

	public DataStream putBInt(int value) {
		return put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(value).array());
	}

	public DataStream putLInt(int value) {
		return put(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(value).array());
	}

	public DataStream putBLong(long value) {
		return put(ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(value).array());
	}

	public DataStream putLLong(long value) {
		return put(ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN).putLong(value).array());
	}

	public DataStream putBFloat(float value) {
		return put(ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putFloat(value).array());
	}

	public DataStream putLFloat(float value) {
		return put(ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putFloat(value).array());
	}
}
