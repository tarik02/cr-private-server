package royaleserver.assets;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipFile;

public class ApkAssetManager extends ZipAssetManager {
	public ApkAssetManager(File file) throws IOException {
		super(new ZipFile(file), "assets/");
	}
}
