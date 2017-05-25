package royaleserver.utils;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Random;

public final class StringUtils {
	private StringUtils() {}

	private static final Charset charset = Charset.forName("UTF-8");

	private static final char[] symbols;
	private static final Random random = new Random();
	private static char[] buffer = new char[64];

	static {
		StringBuilder tmp = new StringBuilder();
		for (char ch = '0'; ch <= '9'; ++ch) {
			tmp.append(ch);
		}

		for (char ch = 'a'; ch <= 'z'; ++ch) {
			tmp.append(ch);
		}

		for (char ch = 'A'; ch <= 'Z'; ++ch) {
			tmp.append(ch);
		}

		symbols = tmp.toString().toCharArray();
	}

	/**
	 * Generates a string using 0-9A-Za-z characters with length between minLength and maxLength.
	 * @param minLength Minimal length of string
	 * @param maxLength Maximal length of string
	 * @return Generated string
	 */
	public static synchronized String randomString(int minLength, int maxLength) {
		return randomString(minLength + random.nextInt(maxLength - minLength + 1));
	}

	/**
	 * Generates a string using 0-9A-Za-z characters for given length.
	 * @param length Length of string
	 * @return Generated string
	 */
	public static synchronized String randomString(int length) {
		if (buffer.length < length) {
			int bufferLength = buffer.length;
			while (bufferLength < length) {
				bufferLength <<= 1;
			}

			buffer = new char[bufferLength];
		}

		for (int i = 0; i < length; ++i) {
			buffer[i] = symbols[random.nextInt(symbols.length)];
		}

		return new String(buffer, 0, length);
	}

	public static String from(InputStream is) {
		return from(is, true);
	}

	public static String from(InputStream is, boolean close) {
		byte[] buffer = IO.getByteArray(is, close);
		if (buffer == null) {
			return null;
		}

		return new String(buffer, charset);
	}
}
