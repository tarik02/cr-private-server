package royaleserver;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import royaleserver.assets.*;
import royaleserver.config.Config;
import royaleserver.database.DataManager;
import royaleserver.database.entity.ClanEntity;
import royaleserver.game.Clan;
import royaleserver.game.Player;
import royaleserver.logic.*;
import royaleserver.network.NetworkServer;
import royaleserver.utils.GsonUtils;
import royaleserver.utils.IO;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.ZipFile;

public class Server {
	private static Logger logger;

	public static final int TICKS_PER_SECOND = 20;


	protected File workingDirectory;

	protected boolean running = false;
	protected long tickCounter = 0;

	protected AssetManager assetManager = null;
	protected DataManager dataManager = null;
	protected NetworkServer networkServer = null;

	protected String resourceFingerprint = "";
	protected Config config;

	protected final Map<Long, Player> players = new ConcurrentHashMap<>();
	protected final Map<Long, Clan> clans = new ConcurrentHashMap<>();

	public Server() throws ServerException {
		this(null);
	}

	public Server(File workingDirectory) throws ServerException {
		if (workingDirectory == null) {
			workingDirectory = new File(".");
		}
		this.workingDirectory = workingDirectory;

		if (!workingDirectory.exists() || !workingDirectory.isDirectory()) {
			throw new ServerException("The working directory is not exists.");
		}

		LogManager.initMainLogger(new File(workingDirectory, "server.log"));
		logger = LogManager.getLogger(Server.class);

		start();
	}

	public void start() throws ServerException {
		if (running) {
			return;
		}
		running = true;
		tickCounter = 0;

		logger.info("Starting the server...");

		logger.info("Reading config...");
		try {
			File configFile = new File(workingDirectory, "config.json");
			int version = 0;

			JsonObject configObject = null;
			if (configFile.exists()) {
				try {
					configObject = new JsonParser().parse(new InputStreamReader(new FileInputStream(configFile))).getAsJsonObject();
					JsonElement jversion = configObject.get("_version");
					if (jversion != null && !jversion.isJsonNull()) {
						version = jversion.getAsInt();
					}
				} catch (Exception ignored) {}
			}

			if (version == 0 || version < Config.CONFIG_VERSION) {
				InputStream configInputStream = Main.class.getResourceAsStream("/config.json");
				if (configInputStream == null) {
					throw new Server.ServerException("Failed to get default config resource.");
				}

				if (version == 0) {
					logger.warn("Resetting your config...");

					final byte[] bytes = IO.getByteArray(configInputStream, true);
					if (bytes == null) {
						throw new ServerException("Failed to get default config.");
					}

					configObject = new JsonParser().parse(new InputStreamReader(new ByteArrayInputStream(bytes))).getAsJsonObject();
					try (OutputStream os = new FileOutputStream(configFile)) {
						os.write(bytes);
					}
				} else if (version < Config.CONFIG_VERSION) {
					JsonObject defaultConfig = new JsonParser().parse(new InputStreamReader(configInputStream)).getAsJsonObject();
					GsonUtils.extendJsonObject(defaultConfig, GsonUtils.ConflictStrategy.PREFER_SECOND_OBJ, configObject);
					configObject = defaultConfig;
				}

				configObject.addProperty("_version", Config.CONFIG_VERSION);

				logger.warn("Saving config...");
				JsonWriter writer = new JsonWriter(new FileWriter(configFile));
				writer.setIndent("\t");
				new Gson().toJson(configObject, writer);
				writer.close();

				logger.warn("Check out your config and start the server.");
				return;
			}

			config = new Config(configObject);
		} catch (Throwable e) {
			logger.fatal("Cannot read config.", e);
			throw new ServerException("Cannot read config.");
		}

		AssetManagerWrapper assetManager = new AssetManagerWrapper();
		JsonArray jassets = config.get("assets").getAsJsonArray();
		for (JsonElement item : jassets) {
			JsonObject jasset = item.getAsJsonObject();
			String provider = jasset.get("provider").getAsString();

			JsonElement jdisabled = jasset.get("disabled");
			if (jdisabled != null && jdisabled.getAsBoolean()) {
				continue;
			}

			AssetManager manager;
			switch (provider) {
			case "folder": {
				JsonElement jpath = jasset.get("path");
				String path;
				if (jpath == null) {
					throw new ServerException("Zip asset provider must have 'path' field.");
				}
				path = jpath.getAsString();

				manager = new FolderAssetManager(new File(workingDirectory, path));
				break;
			}
			case "zip": {
				JsonElement jfile = jasset.get("file");
				String file;
				if (jfile == null) {
					throw new ServerException("Zip asset provider must have 'file' field.");
				}
				file = jfile.getAsString();

				JsonElement jroot = jasset.get("root");
				String root;
				if (jroot == null) {
					root = "";
				} else {
					root = jroot.getAsString();
				}

				try {
					manager = new ZipAssetManager(new ZipFile(new File(workingDirectory, file)), root);
				} catch (IOException e) {
					throw new ServerException("Failed to initialized ZipAssetManager.", e);
				}
				break;
			}
			case "apk": {
				JsonElement jfile = jasset.get("file");
				String file;
				if (jfile == null) {
					throw new ServerException("Zip asset provider must have 'file' field.");
				}
				file = jfile.getAsString();

				try {
					manager = new ApkAssetManager(new File(workingDirectory, file));
				} catch (IOException e) {
					throw new ServerException("Failed to initialized ApkAssetManager.", e);
				}
				break;
			}
			default:
				throw new ServerException("Invalid asset provider '" + provider + "'.");
			}
			assetManager.add(manager);
		}
		this.assetManager = assetManager.simplify();

		resourceFingerprint = assetManager.open("fingerprint.json").content();

		logger.info("Initializing data manager...");
		dataManager = new DataManager(config);

		logger.info("Loading data...");
		Rarity.init(this);
		Arena.init(this);
		Card.init(this);
		GameMode.init(this);
		Chest.init(this);
		ClanBadge.init(this);
		ClanRole.init(this);
		ExpLevel.init(this);

		logger.info("Starting the network thread...");

		networkServer = new NetworkServer(this, config);
		networkServer.start();

		System.gc();
		logger.info("Server started!");

		loop();
	}

	public void stop() {
		if (!running) {
			return;
		}
		running = false;

		if (networkServer != null) {
			logger.info("Stopping the server...");
			networkServer.stop();

			logger.info("Disconnecting the clients...");
			for (Player player : players.values()) {
				player.close("server stopped", true);
			}

			logger.info("Closing the server...");
			networkServer.close();
		}

		if (dataManager != null) {
			logger.info("Stopping data manager...");
			dataManager.stop();
		}

		logger.info("Server stopped!");
	}

	private void loop() {
		final int TICK_TIME = 1000 / TICKS_PER_SECOND;

		while (running) {
			final long startTime = System.currentTimeMillis();
			tick();
			++tickCounter;
			final long endTime = System.currentTimeMillis();
			final long diff = endTime - startTime;

			if (diff < TICK_TIME) {
				try {
					Thread.sleep(TICK_TIME - diff);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected void tick() {
		if (tickCounter % (2.5 * 60 * 20) == 0) { // Every 2.5 minutes
			// TODO: Execute it in each handler thread for each it's player. It's better to do everything with player in the same thread.
			for (Player player : players.values()) {
				try {
					player.updateOnline();
				} catch (RuntimeException e) {
					logger.error("Error while saving player %s.", e, player.getReadableIdentifier());
				}
			}
		}
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public String getResourceFingerprint() {
		return resourceFingerprint;
	}

	public String getContentHash() {
		return config.CONTENT_HASH;
	}

	/**
	 * @param player
	 * @apiNote Internal usage only
	 */
	public void addPlayer(Player player) {
		players.put(player.getEntity().getId(), player);
	}

	/**
	 * @param player
	 * @apiNote Internal usage only
	 */
	public void removePlayer(Player player) {
		players.remove(player.getEntity().getId(), player);
	}

	public Clan resolveClan(Player player) {
		ClanEntity clanEntity = player.getEntity().getClan();
		if (clanEntity == null) {
			return null;
		}

		Clan clan = player.getClan();
		if (clan != null) {
			if (clan.getId() == clanEntity.getId()) {
				return clan;
			}

			clan.removePlayer(player);
		}

		clan = clans.computeIfAbsent(clanEntity.getId(), Clan::new);
		clan.addPlayer(player);

		return clan;
	}

	public void removeClan(Clan clan) {
		clans.remove(clan.getId(), clan);
	}

	public static class ServerException extends Exception {
		public ServerException(String message) {
			super(message);
		}

		public ServerException(String message, Throwable e) {
			super(message, e);
		}
	}
}
