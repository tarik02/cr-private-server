package royaleserver.utils;

public abstract class Logger {
	private final String loggerName;

	public Logger(String name) {
		this.loggerName = name;
	}

	public String name() {
		return loggerName;
	}

	public Logger debug(String message, Object... format) {
		return log(LogLevel.DEBUG, message, format);
	}

	public Logger info(String message, Object... format) {
		return log(LogLevel.INFO, message, format);
	}

	public Logger warn(String message, Object... format) {
		return log(LogLevel.WARN, message, format);
	}

	public Logger error(String message, Object... format) {
		return log(LogLevel.ERROR, message, format);
	}

	public Logger fatal(String message, Object... format) {
		return log(LogLevel.FATAL, message, format);
	}

	public Logger log(LogLevel level, String message, Object... format) {
		send(level, loggerName, message, format);
		return this;
	}

	protected abstract void send(LogLevel level, String name, String message, Object[] format);
}
