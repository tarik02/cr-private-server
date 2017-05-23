package royaleserver.database;

import royaleserver.database.provider.DataProvider;

public abstract class Task {
	/**
	 * Fetch data from the provider.
	 * @param DataProvider provider
	 */
	public abstract void fetch(DataProvider provider);

	/**
	 * Handle fetched data
	 */
	public abstract void handle();
}
