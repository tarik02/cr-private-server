package royaleserver.assets;

public abstract class AssetManager {
	public abstract Asset open(String path);
	public abstract void close();
}
