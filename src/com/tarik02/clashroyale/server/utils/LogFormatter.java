package com.tarik02.clashroyale.server.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
	private final DateFormat DATE_FORMAT = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");

	public synchronized String format(LogRecord record) {
		StringBuilder sb = new StringBuilder();

		sb.append(DATE_FORMAT.format(new Date(record.getMillis())));
		sb.append(" [");
		sb.append(record.getLevel().getName());
		sb.append("/Thread ");
		sb.append(record.getThreadID());
		sb.append("] ");
		sb.append(record.getLoggerName());
		sb.append(": ");
		sb.append(formatMessage(record));
		sb.append("\n");

		return sb.toString();
	}
}
