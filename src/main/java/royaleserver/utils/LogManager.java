package royaleserver.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class LogManager {
	private LogManager() {}

	private static final File DEFAULT_LOG_FILE = new File("server.log");
	private static MainLogger mainLogger = null;

	public static Logger getLogger(Class clazz) {
		return new ChildLogger(getMainLogger(), clazz.getSimpleName());
	}

	private static MainLogger getMainLogger() {
		initMainLogger();

		return mainLogger;
	}

	public static void initMainLogger() {
		initMainLogger(DEFAULT_LOG_FILE);
	}

	public static void initMainLogger(File file) {
		if (mainLogger == null) {
			mainLogger = new MainLogger(false);
			mainLogger.addHandler(System.out);

			try {
				if (!file.exists()) {
					file.createNewFile();
				}

				mainLogger.addHandler(new FileOutputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}

			mainLogger.start();
		}
	}

	public static void shutdown() {
		if (mainLogger != null) {
			mainLogger.stop();
			mainLogger = null;
		}
	}
}
