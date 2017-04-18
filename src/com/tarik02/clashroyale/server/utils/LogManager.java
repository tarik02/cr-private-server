package com.tarik02.clashroyale.server.utils;

import java.io.*;

public class LogManager {
	private LogManager() {}

	private static MainLogger mainLogger = null;

	public static Logger getLogger(Class clazz) {
		return new ChildLogger(getMainLogger(), clazz.getSimpleName());
	}

	private static MainLogger getMainLogger() {
		if (mainLogger == null) {
			mainLogger = new MainLogger(false);
			mainLogger.addHandler(System.out);

			try {
				File file = new File("server.log");

				if (!file.exists()) {
					file.createNewFile();
				}

				mainLogger.addHandler(new FileOutputStream(file));
			} catch (IOException e) {
				e.printStackTrace();
			}

			mainLogger.start();
		}

		return mainLogger;
	}
}
