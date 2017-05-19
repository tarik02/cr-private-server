package royaleserver.database;

import royaleserver.database.provider.DataProvider;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Queue;

public class DataManager {
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
		fetchThread.run();
	}

	public void stop() {
		stopped = true;
		try {
			fetchThread.join();
		} catch (InterruptedException ignored) {}
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
