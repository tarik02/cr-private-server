package royaleserver.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import royaleserver.Server;
import royaleserver.config.Database;
import royaleserver.database.service.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataManager {
	//private final EntityManager entityManager;
	private final SessionFactory sessionFactory;
	private final DataServices services;

	public DataManager(Database config) throws Server.ServerException {
		// Disable hibernate logging
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.WARNING);

		// Disable c3p0 logging
		Properties p = new Properties(System.getProperties());
		p.put("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
		p.put("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
		System.setProperties(p);

		Properties properties = new Properties();

		switch (config.provider) {
		case "mysql":
			properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
			properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
			properties.setProperty("hibernate.connection.url", (new StringBuilder())
					.append("jdbc:mysql://")
					.append(config.mysql.host)
					.append(":")
					.append(config.mysql.port)
					.append("/")
					.append(config.mysql.database)
					.toString());
			properties.setProperty("hibernate.connection.username", config.mysql.user);
			properties.setProperty("hibernate.connection.password", config.mysql.password);
			break;
		default:
			throw new Server.ServerException("Invalid data provider " + config.provider);
		}

		sessionFactory = new Configuration()
				.configure()
				.addProperties(properties)
				.buildSessionFactory();

		services = new DataServices(
				new ArenaService(sessionFactory),
				/*new AssetService(sessionFactory),
				new CardService(sessionFactory),
				new ChestService(sessionFactory),
				new ClanBadgeService(sessionFactory),
				new ClanRoleService(sessionFactory),
				new ClanService(sessionFactory),
				new ExpLevelService(sessionFactory),
				new HomeChestService(sessionFactory),
				new PlayerCardService(sessionFactory),
				new PlayerService(sessionFactory)*/
				null,null,null,null,null,null,null,null,null,null
		);
	}

	public DataServices getServices() {
		return services;
	}

	public ArenaService getArenaService() {
		return services.arenaService;
	}

	public AssetService getAssetService() {
		return services.assetService;
	}

	public CardService getCardService() {
		return services.cardService;
	}

	public ChestService getChestService() {
		return services.chestService;
	}

	public ClanBadgeService getClanBadgeService() {
		return services.clanBadgeService;
	}

	public ClanRoleService getClanRoleService() {
		return services.clanRoleService;
	}

	public ClanService getClanService() {
		return services.clanService;
	}

	public ExpLevelService getExpLevelService() {
		return services.expLevelService;
	}

	public HomeChestService getHomeChestService() {
		return services.homeChestService;
	}

	public PlayerCardService getPlayerCardService() {
		return services.playerCardService;
	}

	public PlayerService getPlayerService() {
		return services.playerService;
	}
}
