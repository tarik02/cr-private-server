package royaleserver.database;

import org.hibernate.SessionFactory;
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
	public final ExpLevelService expLevelService;
	public final HomeChestService homeChestService;
	public final PlayerCardService playerCardService;
	public final PlayerDeckCardService playerDeckCardService;
	public final PlayerService playerService;
	public final UnlockCodeService unlockCodeService;

	public DataServices(SessionFactory sessionFactory) {
		arenaService = new ArenaService(sessionFactory);
		assetService = new AssetService(sessionFactory);
		cardService = new CardService(sessionFactory);
		chestService = new ChestService(sessionFactory);
		clanBadgeService = new ClanBadgeService(sessionFactory);
		clanRoleService = new ClanRoleService(sessionFactory);
		clanService = new ClanService(sessionFactory);
		expLevelService = new ExpLevelService(sessionFactory);
		homeChestService = new HomeChestService(sessionFactory);
		playerCardService = new PlayerCardService(sessionFactory);
		playerDeckCardService = new PlayerDeckCardService(sessionFactory);
		playerService = new PlayerService(sessionFactory);
		unlockCodeService = new UnlockCodeService(sessionFactory);
	}
}
