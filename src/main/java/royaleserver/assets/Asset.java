package royaleserver.assets;

import royaleserver.csv.Table;
import royaleserver.utils.IO;
import royaleserver.utils.StringUtils;

import java.io.InputStream;

public abstract class Asset {
	public abstract InputStream open();
	public abstract String name();
	public abstract long lastUpdated();

	public final byte[] bytes() {
		return IO.getByteArray(open());
	}

	public final String content() {
		return StringUtils.from(open());
	}

	public final Table csv() {
		return new Table(bytes());
	}

	public final boolean isOlderThan(long time) {
		return lastUpdated() < time;
	}
}
