package royaleserver.utils;

import java.io.ByteArrayOutputStream;
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
			ByteArrayOutputStream os = new ByteArrayOutputStream();

			byte[] buffer = new byte[1024];
			int i;

			while ((i = is.read(buffer)) != -1) {
				os.write(buffer, 0, i);
			}

			buffer = os.toByteArray();
			os.close();

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
