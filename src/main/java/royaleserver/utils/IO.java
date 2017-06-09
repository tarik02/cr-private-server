package royaleserver.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

	public static void copy(InputStream is, OutputStream os) {
		copy(is, os, true);
	}

	public static void copy(InputStream is, OutputStream os, boolean close) {
		copy(is, os, close, close);
	}

	public static void copy(InputStream is, OutputStream os, boolean closeIs, boolean closeOs) {
		byte[] temp = new byte[1024];
		int i;

		try {
			while ((i = is.read(temp)) > 0) {
				os.write(temp, 0, i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (closeIs) {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (closeOs) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
