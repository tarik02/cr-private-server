package royaleserver.assets;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class FolderAssetManager extends AssetManager {
	private final File root;

	public FolderAssetManager(File root) {
		this.root = root;
	}

	@Override
	public Asset open(String path) {
		File file = new File(root, path);
		if (file.exists()) {
			return new FileAsset(file.getAbsolutePath().substring(root.getAbsolutePath().length() + File.pathSeparator.length()), file);
		}

		return null;
	}

	private class FileAsset extends Asset {
		private final String name;
		private final File file;

		public FileAsset(String name, File file) {
			this.name = name;
			this.file = file;
		}

		@Override
		public InputStream open() {
			try {
				return new FileInputStream(file);
			} catch (FileNotFoundException ignored) {}

			return null;
		}

		@Override
		public String name() {
			return this.name;
		}

		@Override
		public long lastUpdated() {
			return file.lastModified();
		}
	}
}
