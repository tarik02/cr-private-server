package com.tarik02.clashroyale.server.utils;

public class Hex {
	public static final int DUMP_WIDTH = 16;
	public static final char[] HEX_SET = "0123456789ABCDEF".toCharArray();

	private Hex() {}

	public static String dump(final byte[] buffer) {
		return dump(buffer, 0, buffer.length);
	}

	public static String dump(final byte[] buffer, final int offset, final int count) {
		final StringBuilder builder = new StringBuilder();

		final int parts = (int)Math.ceil(((float)count) / DUMP_WIDTH);
		final int leftPad = String.valueOf((parts * DUMP_WIDTH) + 1).length();
		int position = offset;

		for (int i = 0; i < parts; ++i) {
			builder.append((new String(new char[leftPad - String.valueOf(position).length()])).replace('\0', ' '));
			builder.append(position);
			builder.append(": ");

			final int from = position;
			final int to = Math.min(position + DUMP_WIDTH, offset + count);
			position += DUMP_WIDTH;

			for (int j = from; j < to; ++j) {
				builder.append(HEX_SET[(buffer[j] >> 4) & 0xF]);
				builder.append(HEX_SET[buffer[j] & 0xF]);
				builder.append(" ");
			}

			for (int j = 0; j < DUMP_WIDTH - (to - from); ++j) {
				builder.append("   ");
			}

			builder.append("    ");

			for (int j = from; j < to; ++j) {
				char ch = (char)buffer[j];

				if ((Character.isLetterOrDigit(ch)) || (Character.isSpaceChar(ch))) {
					builder.append(ch);
				} else {
					builder.append(".");
				}
			}

			builder.append("\n");
		}

		return builder.toString();
	}

	public static byte[] toByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
		}

		return data;
	}
}
