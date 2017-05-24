package royaleserver.database;

import royaleserver.Server;
import royaleserver.config.Database;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DataManager {
	private static final Logger logger = LogManager.getLogger(DataManager.class);

	private final EntityManager entityManager;

	private final LinkedBlockingQueue<Task> tasksToFetch = new LinkedBlockingQueue<>();
	private final Queue<Task> tasksToHandle = new ConcurrentLinkedQueue<>();

	private final FetchThread fetchThread;

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

		fetchThread = new FetchThread();
		fetchThread.start();
	}

	public void stop() {
		try {
			tasksToFetch.put(null);
			fetchThread.join();
		} catch (InterruptedException ignored) {}
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

	public class FetchThread extends Thread {
		@Override
		public void run() {
			Task task;

			try {
				while ((task = tasksToFetch.take()) != null) {
					//task.fetch(dataProvider);
					tasksToHandle.add(task);
				}
			} catch (InterruptedException ignored) {}
		}
	}
}
