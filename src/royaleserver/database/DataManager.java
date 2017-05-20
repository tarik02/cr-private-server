package royaleserver.database;

import royaleserver.Server;
import royaleserver.database.provider.DataProvider;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

public class DataManager {
	private static final Logger logger = LogManager.getLogger(DataManager.class);

	public static final int DATABASE_VERSION = 1;

	private DataProvider dataProvider;

	private Queue<Task> tasksToFetch = new ConcurrentLinkedQueue<>();
	private Queue<Task> tasksToHandle = new ConcurrentLinkedQueue<>();

	private boolean stopped = false;
	private FetchThread fetchThread;

	public DataManager(DataProvider provider) {
		dataProvider = provider;
		if (provider == null) {
			throw new NullPointerException("provider");
		}

		fetchThread = new FetchThread();
		fetchThread.start();
	}

	public void stop() {
		stopped = true;
		try {
			fetchThread.join();
		} catch (InterruptedException ignored) {}
	}

	public void update(int version) throws Server.ServerException {
		int currentVersion = dataProvider.fetchVersion();

		for (int i = currentVersion + 1; i <= version; ++i) {
			logger.info("Applying migration #%d...", i);

			if (!dataProvider.applyMigration("migrations.version_" + i)) {
				logger.error("Failed to apply migration #%d!", i);
				throw new Server.ServerException("Failed to apply migrations.");
			}

			logger.error("Migration #%d has applied.", i);
			dataProvider.updateVersion(i);
		}
	}

	public void handleTasks() {
		Task task;
		while ((task = tasksToHandle.poll()) != null) {
			task.handle();
		}
	}

	public class FetchThread extends Thread {
		@Override
		public void run() {
			Task task;
			while (!stopped) {
				while ((task = tasksToFetch.poll()) != null) {
					task.fetch(dataProvider);
					tasksToHandle.add(task);
				}

				try {
					sleep(5);
				} catch (InterruptedException ignored) {}
			}
		}
	}
}
