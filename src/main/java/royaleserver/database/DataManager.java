package royaleserver.database;

import org.reflections.Reflections;
import royaleserver.Server;
import royaleserver.database.model.Model;
import royaleserver.database.model.ModelInfo;
import royaleserver.database.provider.DataProvider;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataManager {
	private static final Logger logger = LogManager.getLogger(DataManager.class);
	public static final int DATABASE_VERSION = 1;

	private DataProvider dataProvider;

	private LinkedBlockingQueue<Task> tasksToFetch = new LinkedBlockingQueue<>();
	private Queue<Task> tasksToHandle = new ConcurrentLinkedQueue<>();

	private boolean stopped = false;
	private FetchThread fetchThread;

	public DataManager(DataProvider provider) {
		dataProvider = provider;
		if (provider == null) {
			throw new NullPointerException("provider");
		}

		init();

		fetchThread = new FetchThread();
		fetchThread.start();
	}

	public void stop() {
		stopped = true;
		try {
			tasksToFetch.put(null);
			fetchThread.join();
		} catch (InterruptedException ignored) {}
	}

	/**
	 * @apiNote Internal usage only
	 * @param version Target version
	 * @throws Server.ServerException If can't be updated
	 */
	public void update(int version) throws Server.ServerException {
		int currentVersion = dataProvider.fetchVersion();

		for (int i = currentVersion + 1; i <= version; ++i) {
			logger.info("Applying migration #%d...", i);

			if (!dataProvider.applyMigration("migrations.version_" + i)) {
				logger.error("Failed to apply migration #%d!", i);
				throw new Server.ServerException("Failed to apply migrations.");
			}

			logger.info("Migration #%d has applied.", i);
			dataProvider.updateVersion(i);
		}
	}

	/**
	 * @apiNote Internal usage only
	 */
	public void handleTasks() {
		Task task;
		while ((task = tasksToHandle.poll()) != null) {
			task.handle();
		}
	}

	/**
	 * Add task to execution queue
	 * @param task Task to execute
	 */
	public void task(Task task) {
		try {
			tasksToFetch.put(task);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static final Map<Class<? extends Model>, ModelInfo> models = new HashMap<>();
	private static boolean initialized = false;

	public static void init() {
		if (initialized) {
			return;
		}
		initialized = true;

		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
			System.out.println("Please, add mysql jdbc driver.");
		}

		try {
			Class.forName("org.sqlite.JDBC").newInstance();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException ignored) {
			System.out.println("Please, add sqlite jdbc driver.");
		}

		Reflections reflections = new Reflections("royaleserver.database.model");
		Set<Class<? extends Model>> allClasses = reflections.getSubTypesOf(Model.class);
		for (Class<? extends Model> clazz : allClasses) {
			ArrayList<String> keys = new ArrayList<>();
			String table;

			int counter = 0;
			for (Field field : clazz.getDeclaredFields()) {
				int modifiers = field.getModifiers();

				if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers) && Modifier.isPublic(modifiers) && field.getType().equals(int.class)) {
					String name = field.getName();
					if (name.startsWith("KEY_")) {
						name = name.substring(4).toLowerCase();
						int index = 0;
						try {
							index = field.getInt(null);
						} catch (IllegalAccessException ignored) {
							System.exit(-1);
						}

						if (index != counter) {
							System.out.println("Key [" + clazz.getName() + "." + field.getName() + "] must be " + index + ".");
							System.exit(-1);
						}
						++counter;

						keys.add(name);
					}
				}
			}

			try {
				table = (String)clazz.getDeclaredField("TABLE").get(null);
			} catch (IllegalAccessException | NoSuchFieldException ignored) {
				table = clazz.getName();
				if (table.endsWith("Model")) {
					table = table.substring(0, table.length() - 5);
				}

				table = table.toLowerCase() + "s";
			}

			models.put(clazz, new ModelInfo(table, clazz, keys.toArray(new String[0])));
		}
	}

	public static ModelInfo getModelInfo(Class<? extends Model> clazz) {
		return models.get(clazz);
	}


	public class FetchThread extends Thread {
		@Override
		public void run() {
			Task task;

			try {
				while ((task = tasksToFetch.take()) != null) {
					task.fetch(dataProvider);
					tasksToHandle.add(task);
				}
			} catch (InterruptedException ignored) {}
		}
	}
}
