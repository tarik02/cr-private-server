package royaleserver.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.reflections.Reflections;
import royaleserver.Server;
import royaleserver.config.Config;
import royaleserver.database.service.*;

import javax.persistence.Entity;
import java.lang.reflect.Modifier;
import java.util.Properties;

public class DataManager {
	private final SessionFactory sessionFactory;
	private final DataServices services;

	public DataManager(Config config) throws Server.ServerException {
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

		properties.setProperty("hibernate.show_sql", config.get("database.show_sql").getAsBoolean() ? "true" : "false");

		switch (config.get("database.provider").getAsString()) {
		case "mysql":
			properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
			properties.setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");

			properties.setProperty("hibernate.connection.CharSet", "utf8");
			properties.setProperty("hibernate.connection.characterEncoding", "utf8");
			properties.setProperty("hibernate.connection.useUnicode", "true");

			properties.setProperty("hibernate.connection.url", (new StringBuilder())
					.append("jdbc:mysql://")
					.append(config.get("database.mysql.host").getAsString())
					.append(":")
					.append(config.get("database.mysql.port").getAsShort())
					.append("/")
					.append(config.get("database.mysql.database").getAsString())
					.toString());

			properties.setProperty("hibernate.connection.username", config.get("database.mysql.user").getAsString());
			properties.setProperty("hibernate.connection.password", config.get("database.mysql.password").getAsString());
			break;
		default:
			throw new Server.ServerException("Invalid data provider " + config.get("database.provider").getAsString());
		}

		Configuration configuration = new Configuration()
				.configure()
				.addProperties(properties);

		configuration.addPackage("royaleserver.database.entity");
		for (Class<?> clazz : (new Reflections("royaleserver.database.entity")).getTypesAnnotatedWith(Entity.class)) {
			if (!Modifier.isAbstract(clazz.getModifiers())) {
				configuration.addAnnotatedClass(clazz);
			}
		}

		sessionFactory = configuration.buildSessionFactory();
		services = new DataServices(sessionFactory);
	}

	public void stop() {
		sessionFactory.close();
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

	public UnlockCodeService getUnlockCodeService() {
		return services.unlockCodeService;
	}
}
