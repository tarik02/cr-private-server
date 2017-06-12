package royaleserver.game;

import royaleserver.Server;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

public class Main {
	private static Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) throws Throwable {
		try {
			new Server().stop();
		} catch (Throwable e) {
			logger.fatal(e.getMessage(), e);
		}

		LogManager.shutdown();
	}
}
