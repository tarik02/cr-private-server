package royaleserver;

import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

public class Main {
	private static Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) throws Throwable {
		try {
			Server server = new Server();
			try {
				if (server.start()) {
					System.gc();
					server.loop();
				}
			} finally {
				server.stop();
			}
		} catch (Throwable e) {
			logger.fatal(e.getMessage(), e);
		}

		LogManager.shutdown();
	}
}
