package royaleserver;

import com.google.gson.Gson;
import royaleserver.assets.AssetManager;
import royaleserver.assets.FolderAssetManager;
import royaleserver.config.Config;
import royaleserver.database.DataManager;
import royaleserver.logic.*;
import royaleserver.network.NetworkServer;
import royaleserver.utils.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashSet;
import java.util.Set;

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

	protected final Set<Player> players = new LinkedHashSet<>();



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
			config = (new Gson()).fromJson(new InputStreamReader(new FileInputStream(new File(workingDirectory, "config.json"))), Config.class);
		} catch (Throwable e) {
			logger.fatal("Cannot read config.", e);
			throw new ServerException("Cannot read config.");
		}

		if (config.version < Config.CONFIG_VERSION) {
			logger.fatal("Config is too old.\n\tCurrent version: %d.\n\tRequired version: %d.", config.version, Config.CONFIG_VERSION);
			throw new ServerException("Config is too old.");
		}

		assetManager = new FolderAssetManager(new File(workingDirectory, "assets"));
		resourceFingerprint = assetManager.open("fingerprint.json").content();

		logger.info("Initializing data manager...");
		dataManager = new DataManager(config.database);

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

		networkServer = new NetworkServer(this);
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

		logger.info("Stopping the server...");
		networkServer.stop();

		logger.info("Disconnecting the clients...");
		for (Player player : players) {
			player.close("server stopped", true);
		}

		logger.info("Closing the server...");
		networkServer.close();

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
		if (tickCounter % 2.5 * 60 * 20 == 0) { // Every 2.5 minutes
			synchronized (players) {
				for (Player player : players) {
					player.updateOnline();
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

	/**
	 * @apiNote Internal usage only
	 * @param player
	 */
	public void addPlayer(Player player) {
		synchronized (players) {
			players.add(player);
		}
	}

	/**
	 * @apiNote Internal usage only
	 * @param player
	 */
	public void removePlayer(Player player) {
		synchronized (players) {
			players.remove(player);
		}
	}

	public static class ServerException extends Exception {
		public ServerException(String message) {
			super(message);
		}
	}
}
