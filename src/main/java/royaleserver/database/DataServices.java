package royaleserver.database;

import royaleserver.database.service.AssetService;
import royaleserver.database.service.PlayerService;

/**
 * A class that contains all the services.
 */
public final class DataServices {
	public final AssetService assetService;
	public final PlayerService playerService;

	public DataServices(AssetService assetService, PlayerService playerService) {
		this.assetService = assetService;
		this.playerService = playerService;
	}
}
