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
	public final ClanBadgeService clanBadgeService;
	public final ClanRoleService clanRoleService;
	public final ClanService clanService;
	public final HomeChestService homeChestService;
	public final PlayerCardService playerCardService;
	public final PlayerService playerService;

	public DataServices(ArenaService arenaService, AssetService assetService, CardService cardService, ChestService chestService, ClanBadgeService clanBadgeService, ClanRoleService clanRoleService, ClanService clanService, HomeChestService homeChestService, PlayerCardService playerCardService, PlayerService playerService) {
		this.arenaService = arenaService;
		this.assetService = assetService;
		this.cardService = cardService;
		this.chestService = chestService;
		this.clanBadgeService = clanBadgeService;
		this.clanRoleService = clanRoleService;
		this.clanService = clanService;
		this.homeChestService = homeChestService;
		this.playerCardService = playerCardService;
		this.playerService = playerService;
	}
}
