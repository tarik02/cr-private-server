package royaleserver.utils;

import org.tukaani.xz.LZMAInputStream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public final class CSVConverter {
	private CSVConverter() {}

	public static String decodeCSV(byte[] buffer) {
		byte[] tempBuffer = new byte[buffer.length + 4];
		System.arraycopy(buffer, 0, tempBuffer, 0, 9);
		Arrays.fill(tempBuffer, 9, 13, (byte)0x00);
		System.arraycopy(buffer, 9, tempBuffer, 9 + 4, buffer.length - 9);

		ByteArrayOutputStream os = null;
		try {
			LZMAInputStream is = new LZMAInputStream(new ByteArrayInputStream(tempBuffer));
			os = new ByteArrayOutputStream();

			tempBuffer = new byte[256];
			int i;
			while ((i = is.read(tempBuffer)) > 0) {
				os.write(tempBuffer, 0, i);
			}
		} catch (Exception ignored) {}

		return os == null ? null : new String(os.toByteArray());
	}
}
