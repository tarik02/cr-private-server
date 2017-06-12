package royaleserver.assets;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipAssetManager extends AssetManager {
	private final ZipFile file;
	private final String root;

	public ZipAssetManager(ZipFile file) {
		this(file, "");
	}

	public ZipAssetManager(ZipFile file, String root) {
		if (file == null) {
			throw new IllegalArgumentException("file");
		}

		if (root == null) {
			throw new IllegalArgumentException("root");
		}

		if (root.length() != 0 && !root.endsWith("/")) {
			root = root + "/";
		}

		this.file = file;
		this.root = root;
	}

	@Override
	public Asset open(String path) {
		ZipEntry entry = file.getEntry(root + path);
		if (entry == null) {
			return null;
		}

		return new ZipEntryAsset(path, file, entry);
	}

	@Override
	public void close() {
		try {
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ZipEntryAsset extends Asset {
		private final String name;
		private final ZipFile file;
		private final ZipEntry entry;

		public ZipEntryAsset(String name, ZipFile file, ZipEntry entry) {
			this.name = name;
			this.file = file;
			this.entry = entry;
		}

		@Override
		public InputStream open() {
			try {
				return file.getInputStream(entry);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		@Override
		public String name() {
			return name;
		}

		@Override
		public long lastUpdated() {
			return entry.getLastModifiedTime().toMillis();
		}
	}
}
