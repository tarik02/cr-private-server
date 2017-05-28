package royaleserver.assets;

import royaleserver.csv.Table;
import royaleserver.database.entity.AssetEntity;
import royaleserver.utils.IO;
import royaleserver.utils.StringUtils;

import java.io.InputStream;
import java.util.Date;

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

	public final boolean isNewerThan(long time) {
		return lastUpdated() < time;
	}

	public final boolean isNewerThan(Date date) {
		return isNewerThan(date.getTime());
	}

	public final boolean isNewerThan(AssetEntity assetEntity) {
		return isNewerThan(assetEntity.getLastUpdated());
	}
}
