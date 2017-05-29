package royaleserver.database;

import royaleserver.Server;
import royaleserver.config.Database;
import royaleserver.database.service.*;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DataManager {
	private final EntityManager entityManager;
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

		Map<String, String> properties = new HashMap<>();

		switch (config.provider) {
		case "mysql":
			properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
			properties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
			properties.put("hibernate.connection.url", (new StringBuilder())
					.append("jdbc:mysql://")
					.append(config.mysql.host)
					.append(":")
					.append(config.mysql.port)
					.append("/")
					.append(config.mysql.database)
					.toString());
			properties.put("hibernate.connection.username", config.mysql.user);
			properties.put("hibernate.connection.password", config.mysql.password);
			break;
		default:
			throw new Server.ServerException("Invalid data provider " + config.provider);
		}

		entityManager = Persistence.createEntityManagerFactory("royaleserver", properties).createEntityManager();
		services = new DataServices(
				new ArenaService(entityManager),
				new AssetService(entityManager),
				new CardService(entityManager),
				new ChestService(entityManager),
				new ClanService(entityManager),
				new HomeChestService(entityManager),
				new PlayerCardService(entityManager),
				new PlayerService(entityManager)
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

	public ClanService getClanService() {
		return services.clanService;
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
