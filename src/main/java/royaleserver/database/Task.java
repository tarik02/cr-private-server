package royaleserver.database;

public abstract class Task {
	/**
	 * Fetch data from the provider.
	 * @param DataProvider provider
	 */
	//public abstract void fetch(DataProvider provider);

	/**
	 * Handle fetched data
	 */
	public abstract void handle();
}
