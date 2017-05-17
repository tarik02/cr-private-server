package royaleserver.utils;

public final class LogLevel {
	public static final int WEIGHT_DEBUG = 1;
	public static final int WEIGHT_INFO = 2;
	public static final int WEIGHT_WARN = 3;
	public static final int WEIGHT_ERROR = 4;
	public static final int WEIGHT_FATAL = 5;

	public static final LogLevel DEBUG = new LogLevel(WEIGHT_DEBUG, "DEBUG");
	public static final LogLevel INFO = new LogLevel(WEIGHT_INFO, "INFO");
	public static final LogLevel WARN = new LogLevel(WEIGHT_WARN, "WARN");
	public static final LogLevel ERROR = new LogLevel(WEIGHT_ERROR, "ERROR");
	public static final LogLevel FATAL = new LogLevel(WEIGHT_FATAL, "FATAL");

	private final int levelWeight;
	private final String levelName;

	private LogLevel(int weight, String name) {
		levelWeight = weight;
		levelName = name;
	}

	public String name() {
		return levelName;
	}

	public int weight() {
		return levelWeight;
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	@Override
	public String toString() {
		return levelName;
	}
}
