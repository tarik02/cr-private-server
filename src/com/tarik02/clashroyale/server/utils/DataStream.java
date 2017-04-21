package com.tarik02.clashroyale.server.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class DataStream {
	private final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	protected byte[] buffer;
	protected int offset, count;
	protected ByteBuffer byteBuffer;

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

		byteBuffer = ByteBuffer.allocate(8);
	}

	public DataStream reset() {
		return reset(false);
	}

	public DataStream reset(boolean saveBufferCapacity) {
		if (saveBufferCapacity) {
			Arrays.fill(buffer, (byte)0);
		} else {
			buffer = new byte[32];
		}

		offset = 0;
		count = 0;

		return this;
	}

	public byte[] getBuffer() {
		return Arrays.copyOf(buffer, count);
	}

	public DataStream setBuffer(byte[] buffer) {
		if (buffer == null) {
			this.buffer = new byte[32];
			count = 0;
		} else {
			this.buffer = buffer;
			count = buffer.length;
		}

		return this;
	}

	public int getOffset() {
		return offset;
	}

	public DataStream setOffset(int offset) {
		ensureCapacity(offset);
		this.offset = offset;

		return this;
	}

	public int getCount() {
		return count;
	}
	public int remaining() {
		return count - offset;
	}

	protected void ensureCapacity(int capacity) {
		while (buffer.length < capacity) {
			buffer = Arrays.copyOf(buffer, buffer.length << 1);
		}
	}


	public DataStream skip(int count) {
		offset += count;
		return this;
	}


	public byte[] get() {
		byte[] result = Arrays.copyOfRange(buffer, offset, count);
		offset = count;
		return result;
	}

	public byte[] get(int len) {
		len = Math.min(len, buffer.length - offset);

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
			return buffer[offset++];
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
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.BIG_ENDIAN).put(get(2)).getShort(0);
	}

	public DataStream putBShort(short value) {
		return put(byteBuffer.order(ByteOrder.BIG_ENDIAN).putShort(0, value).array(), 0, 2);
	}

	public short getLShort() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).put(get(2)).getShort(0);
	}

	public DataStream putLShort(short value) {
		return put(byteBuffer.order(ByteOrder.LITTLE_ENDIAN).putShort(0, value).array(), 0, 2);
	}


	public int getBTriad() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.BIG_ENDIAN).put(0, (byte)0x00).put(get(3)).getInt(0);
	}

	public DataStream putBTriad(int value) {
		return put(byteBuffer.order(ByteOrder.BIG_ENDIAN).putInt(0, value).array(), 1, 3);
	}

	public int getLTriad() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).put(get(3)).put((byte)0x00).getInt(0);
	}

	public DataStream putLTriad(int value) {
		return put(byteBuffer.order(ByteOrder.LITTLE_ENDIAN).putInt(0, value).array(), 0, 3);
	}


	public int getBInt() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.BIG_ENDIAN).put(get(4)).getInt(0);
	}

	public DataStream putBInt(int value) {
		return put(byteBuffer.order(ByteOrder.BIG_ENDIAN).putInt(0, value).array(), 0, 4);
	}

	public int getLInt() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).put(get(4)).getInt(0);
	}

	public DataStream putLInt(int value) {
		return put(byteBuffer.order(ByteOrder.LITTLE_ENDIAN).putInt(0, value).array(), 0, 4);
	}


	public long getBLong() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.BIG_ENDIAN).put(get(8)).getLong(0);
	}

	public DataStream putBLong(long value) {
		return put(byteBuffer.order(ByteOrder.BIG_ENDIAN).putLong(0, value).array(), 0, 8);
	}

	public long getLLong() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).put(get(8)).getLong(0);
	}

	public DataStream putLLong(long value) {
		return put(byteBuffer.order(ByteOrder.LITTLE_ENDIAN).putLong(0, value).array(), 0, 8);
	}


	public float getBFloat() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.BIG_ENDIAN).put(get(4)).getFloat(0);
	}

	public DataStream putBFloat(float value) {
		return put(byteBuffer.order(ByteOrder.BIG_ENDIAN).putFloat(0, value).array(), 0, 4);
	}

	public float getLFloat() {
		byteBuffer.position(0);
		return byteBuffer.order(ByteOrder.LITTLE_ENDIAN).put(get(4)).getFloat(0);
	}

	public DataStream putLFloat(float value) {
		return put(byteBuffer.order(ByteOrder.LITTLE_ENDIAN).putFloat(0, value).array(), 0, 4);
	}


	public int getRrsInt32() {
		short c = 0;
		long value = 0;
		long b;
		long seventh;
		long msb;

		do {
			b = getByte() & 0xFF;

			if (c == 0) {
				seventh = (b & 0x40) >> 6; // save 7th bit
				msb = (b & 0x80) >> 7; // save msb
				b = b << 1; // rotate to the left
				b = b & ~(0x181); // clear 8th and 1st bit and 9th if any
				b = b | (msb << 7) | (seventh); // insert msb and 6th back in
			}

			value |= (b & 0x7f) << (7 * c);
			++c;
		} while ((b & 0x80) > 0);

		value = (value >>> 1) ^ -(value & 1);
		return (int)value;
	}

	public DataStream putRrsInt32(int value) {
		long lvalue = value;
		int size = calculateVarInt32(lvalue);
		boolean rotate = true;
		long b;

		lvalue = (lvalue << 1) ^ (lvalue >> 31);
		while (lvalue != 0) {
			b = (lvalue & 0x7f);

			if (lvalue >= 0x80) {
				b |= 0x80;
			} if (rotate) {
				rotate = false;
				long lsb = b & 0x1;
				long msb = (b & 0x80) >> 7;
				b = b >> 1; // rotate to the right
				b = b & ~(0xC0); // clear 7th and 6th bit
				b = b | (msb << 7) | (lsb << 6); // insert msb and lsb back in
			}

			putByte((byte)b);
			lvalue >>>= 7;
		}

		return this;
	}


	public long getRrsLong() {
		return (((long)getRrsInt32()) << 32) | ((long)getRrsInt32());
	}

	public DataStream putRrsLong(long value) {
		return putRrsInt32((int)(value >> 32)).putRrsInt32((int)value);
	}


	public String getString() {
		int count = getBInt();
		if (count == 0xFFFFFFFF) {
			return "";
		}

		return new String(get(count), UTF8_CHARSET);
	}

	public DataStream putString(String value) {
		if (value.length() == 0) {
			putBInt(0xFFFFFFFF);
		} else {
			byte[] bytes = value.getBytes(UTF8_CHARSET);
			putBInt(bytes.length);
			put(bytes);
		}

		return this;
	}


	public String getZipString() {
		int length = getBInt() - 4;
		int zlength = getLInt();

		if (remaining() > length) {
			Inflater decompressor = new Inflater();
			decompressor.setInput(buffer, offset, length);
			offset += length;

			try {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(zlength);
				byte[] buf = new byte[1024];
				int count;
				while ((!decompressor.finished()) && ((count = decompressor.inflate(buf)) > 0)) {
					bos.write(buf, 0, count);
				}
				decompressor.end();
				bos.close();

				return new String(bos.toByteArray(), UTF8_CHARSET);
			} catch (DataFormatException | IOException ignored) {}
		}

		return null;
	}

	public DataStream putZipString(String value) {
		Deflater compressor = new Deflater();
		byte[] buffer = value.getBytes(UTF8_CHARSET);
		compressor.setInput(buffer);

		int sizeOffset = count;
		putBInt(0).putLInt(buffer.length);

		byte[] buf = new byte[1024];
		int length = 0;

		compressor.finish();
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			put(buf, 0, count);
			length += count;
		}
		compressor.end();

		int endOffset = count;
		count = sizeOffset;
		putBInt(length);
		count = endOffset;

		return this;
	}


	public Bitset getBitset() {
		return new Bitset(getByte());
	}

	public DataStream putBitset(Bitset value) {
		return putByte(value.getValue());
	}


	public byte[] getByteSet() {
		int len = getBInt();
		if (len == 0xFFFFFFFF) {
			return new byte[0];
		}

		return get(len);
	}

	public DataStream putByteSet(byte[] value) {
		if (value.length == 0) {
			return putBInt(0xFFFFFFFF);
		}

		return putBInt(value.length).put(value);
	}


	public SCID getSCID() {
		int high = getRrsInt32();

		if (high > 0) {
			int low = getRrsInt32();
			return new SCID(high, low);
		}

		return new SCID();
	}

	public DataStream putSCID(SCID value) {
		if (value.getHigh() > 0) {
			return putRrsInt32(value.getHigh()).putRrsInt32(value.getLow());
		}

		return putRrsInt32(0);
	}


	public int getVarInt32() {
		int result = 0;
		int shift = 0;
		int b;
		do {
			if (shift >= 32) {
				return 0;
			}

			b = getByte();
			result |= (b & 0x7F) << shift;
			shift += 7;
		} while ((b & 0x80) != 0);

		return result;
	}

	public DataStream putVarInt32(int value) {
		do {
			int bits = value & 0x7F;
			value >>>= 7;
			byte b = (byte)(bits + ((value != 0) ? 0x80 : 0));
			putByte(b);
		} while (value != 0);

		return this;
	}


	public String dump() {
		return Hex.dump(buffer, 0, count);
	}

	public DataStream printDump() {
		System.out.println(dump());
		return this;
	}


	protected static int calculateVarInt32(long value) {
		if (value < 1 << 7) {
			return 1;
		}

		if (value < 1 << 14) {
			return 2;
		}

		if (value < 1 << 21) {
			return 3;
		}

		if (value < 1 << 28) {
			return 4;
		}

		return 5;
	}
}
