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
	public final PlayerService playerService;
	public final UnlockCodeService unlockCodeService;

	public DataServices(SessionFactory sessionFactory) {
		this.arenaService = new ArenaService(sessionFactory);
		this.assetService = new AssetService(sessionFactory);
		this.cardService = new CardService(sessionFactory);
		this.chestService = new ChestService(sessionFactory);
		this.clanBadgeService = new ClanBadgeService(sessionFactory);
		this.clanRoleService = new ClanRoleService(sessionFactory);
		this.clanService = new ClanService(sessionFactory);
		this.expLevelService = new ExpLevelService(sessionFactory);
		this.homeChestService = new HomeChestService(sessionFactory);
		this.playerCardService = new PlayerCardService(sessionFactory);
		this.playerService = new PlayerService(sessionFactory);
		this.unlockCodeService = new UnlockCodeService(sessionFactory);
	}
}
