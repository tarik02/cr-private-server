package royaleserver;

import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

public class Main {
	private static Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) throws Throwable {
		try {
			new Server();
		} catch (Server.ServerException e) {
			logger.fatal(e.getMessage(), e);
		}
	}
}
