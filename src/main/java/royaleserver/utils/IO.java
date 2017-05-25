package royaleserver.utils;

import java.io.IOException;
import java.io.InputStream;

public final class IO {
	private IO() {}

	public static byte[] getByteArray(InputStream is) {
		return getByteArray(is, true);
	}

	public static byte[] getByteArray(InputStream is, boolean close) {
		try {
			byte[] buffer = new byte[is.available()];
			is.read(buffer);

			if (close) {
				try {
					is.close();
				} catch (IOException ignored) {}
			}

			return buffer;
		} catch (IOException ignored) {}

		return null;
	}
}
