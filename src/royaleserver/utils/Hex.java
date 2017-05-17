package royaleserver.utils;

import java.util.regex.Pattern;

public class Hex {
	public static final int DUMP_WIDTH = 16;
	public static final char[] HEX_SET = "0123456789ABCDEF".toCharArray();
	private static final byte[] PRINTABLE = new byte[] {(byte)0, (byte)0, (byte)0, (byte)0, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)255, (byte)127, (byte)255, (byte)255, (byte)255, (byte)127, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0, (byte)0};
	private static final Pattern REMOVE_NOT_HEX_PATTERN = Pattern.compile("[^0-9a-fA-F]");

	private Hex() {}

	private static boolean isPrintable(char ch) {
		int index = (int)ch;
		if (index > 255) {
			return false;
		}

		return ((PRINTABLE[index / 8] >> (index % 8)) & 0x01) == 1;
	}

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

				if (isPrintable(ch)) {
					builder.append(ch);
				} else {
					builder.append(".");
				}
			}

			builder.append("\n");
		}

		return builder.toString();
	}

	public static String toHexString(byte[] buffer) {
		return toHexString(buffer, 0, buffer.length);
	}

	public static String toHexString(byte[] buffer, int offset, int count) {
		StringBuilder sb = new StringBuilder((count - offset) * 2);
		for (int i = offset; i < offset + count; ++i) {
			sb.append(HEX_SET[(buffer[i] >> 4) & 0xF]);
			sb.append(HEX_SET[buffer[i] & 0xF]);
		}
		return sb.toString();
	}

	public static byte[] toByteArray(String s) {
		s = REMOVE_NOT_HEX_PATTERN.matcher(s).replaceAll("");

		int len = s.length();
		byte[] data = new byte[len / 2];

		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte)((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
		}

		return data;
	}
}
