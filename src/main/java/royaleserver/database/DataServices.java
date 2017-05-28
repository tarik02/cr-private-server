package royaleserver.database;

import royaleserver.database.service.*;

/**
 * A class that contains all the services.
 */
public final class DataServices {
	public final ArenaService arenaService;
	public final AssetService assetService;
	public final CardService cardService;
	public final ChestService chestService;
	public final PlayerCardService playerCardService;
	public final PlayerService playerService;

	public DataServices(ArenaService arenaService, AssetService assetService, CardService cardService, ChestService chestService, PlayerCardService playerCardService, PlayerService playerService) {
		this.arenaService = arenaService;
		this.assetService = assetService;
		this.cardService = cardService;
		this.chestService = chestService;
		this.playerCardService = playerCardService;
		this.playerService = playerService;
	}
}
