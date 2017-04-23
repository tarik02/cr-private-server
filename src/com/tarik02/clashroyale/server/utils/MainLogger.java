package com.tarik02.clashroyale.server.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public final class MainLogger extends Logger {
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]");
	private Queue<String> logQueue = new ConcurrentLinkedQueue<>();
	private List<OutputStream> handlers = new ArrayList<>();

	private boolean running = false;
	private LoggerThread thread = new LoggerThread();

	public MainLogger() {
		this(true);
	}

	public MainLogger(boolean start) {
		super("");
		if (start) {
			this.start();
		}
	}

	public void addHandler(OutputStream stream) {
		handlers.add(stream);
	}

	@Override
	protected void send(LogLevel level, String name, String message, Object[] format) {
		StringBuilder sb = new StringBuilder(1024);

		Throwable throwable = null;
		if ((format.length != 0) && (format[0] instanceof Throwable)) {
			throwable = (Throwable)format[0];
			for (int i = 1; i != format.length; ++i) {
				format[i - 1] = format[i];
			}

			format[format.length - 1] = null;
		}

		sb.append(DATE_FORMAT.format(new Date()));
		sb.append(" [");
		sb.append(level.name());
		sb.append("/");

		String threadName = Thread.currentThread().getName();
		if (threadName.equals("main")) {
			sb.append("Main thread");
		} else {
			sb.append("Thread ");
			sb.append(threadName);
		}

		sb.append("] ");

		if (name.equals("")) {
			sb.append("Main");
		} else {
			sb.append(name);
		}

		sb.append(": ");
		sb.append(String.format(message, format));
		sb.append('\n');

		if (throwable != null) {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			PrintStream stream = new PrintStream(os);
			throwable.printStackTrace(stream);
			sb.append(new String(os.toByteArray()));
			sb.append('\n');
		}

		logQueue.add(sb.toString());
	}

	public void start() {
		if (!running) {
			running = true;
			thread.start();
		}
	}

	public void stop() {
		if (running) {
			running = false;

			try {
				thread.join();
			} catch (InterruptedException ignored) {}

			thread = null;
		}
	}

	private class LoggerThread extends Thread {
		@Override
		public void run() {
			String line;

			while (running) {
				while ((line = logQueue.poll()) != null) {
					for (OutputStream handler : handlers) {
						try {
							handler.write(line.getBytes());
						} catch (IOException ignored) {}
					}
				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException ignored) {}

				line = null;
			}
		}
	}
}
