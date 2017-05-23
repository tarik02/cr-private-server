package royaleserver;

import royaleserver.database.DataManager;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Main {
	private static Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) throws Throwable {
		try {
			DataManager.init();

			File configFile = new File("config.json");
			if (!configFile.exists()) {
				InputStream configInputStream = Main.class.getResourceAsStream("/config.json");
				if (configInputStream == null) {
					throw new Server.ServerException("Failed to get config resource.");
				}

				OutputStream configOutputStream = new FileOutputStream(configFile);
				int i;
				byte[] buffer = new byte[1024];

				while ((i = configInputStream.read(buffer)) > 0) {
					configOutputStream.write(buffer, 0, i);
				}

				configInputStream.close();
				configOutputStream.close();
				configInputStream = null;
				configOutputStream = null;
				configFile = null;
			}

			new Server();
		} catch (Throwable e) {
			logger.fatal(e.getMessage(), e);
		}
	}
}
