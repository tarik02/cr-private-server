package com.tarik02.clashroyale.server.utils;

import java.io.*;
import java.util.Properties;
import java.util.logging.Logger;

public class LogManager {
	private static java.util.logging.LogManager logManager = null;

	private LogManager() {}

	public static Logger getLogger(Class clazz) {
		getLogManager();

		String name = clazz.getSimpleName();

		return Logger.getLogger(name);
	}

	private static java.util.logging.LogManager getLogManager() {
		if (logManager == null) {
			logManager = java.util.logging.LogManager.getLogManager();

			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream(1024);
				getProperties().store(os, "");

				logManager.readConfiguration(new ByteArrayInputStream(os.toByteArray()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return logManager;
	}

	private static Properties getProperties() {
		Properties properties = new Properties();

		properties.setProperty("handlers", "java.util.logging.ConsoleHandler, java.util.logging.FileHandler");

		properties.setProperty("java.util.logging.ConsoleHandler.formatter", LogFormatter.class.getName());

		properties.setProperty("java.util.logging.FileHandler.formatter", LogFormatter.class.getName());
		properties.setProperty("java.util.logging.FileHandler.pattern", "server.log");
		properties.setProperty("java.util.logging.FileHandler.append", "true");

		return properties;
	}
}
