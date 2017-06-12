package royaleserver.assets;

import java.util.ArrayList;
import java.util.List;

public class AssetManagerWrapper extends AssetManager {
	private final List<AssetManager> assetManagers = new ArrayList<>();

	/**
	 * The first added manager has the highest priority.
	 * @param assetManager AssetManager instance to use
	 */
	public void add(AssetManager assetManager) {
		assetManagers.add(assetManager);
	}

	public AssetManager simplify() {
		if (assetManagers.size() == 1) {
			return assetManagers.get(0);
		}

		return this;
	}

	@Override
	public Asset open(String path) {
		for (AssetManager assetManager : assetManagers) {
			Asset asset = assetManager.open(path);
			if (asset != null) {
				return asset;
			}
		}

		return null;
	}

	@Override
	public void close() {
		for (AssetManager assetManager : assetManagers) {
			assetManager.close();
		}

		assetManagers.clear();
	}
}
