package com.tarik02.clashroyale.server;

import com.tarik02.clashroyale.server.utils.LogManager;
import com.tarik02.clashroyale.server.utils.Logger;

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
