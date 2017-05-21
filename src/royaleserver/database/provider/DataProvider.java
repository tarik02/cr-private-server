package royaleserver.database.provider;

import royaleserver.database.model.PlayerModel;

public abstract class DataProvider {
	public abstract boolean applyMigration(String name);

	public abstract int fetchVersion();
	public abstract void updateVersion(int version);

	public PlayerModel fetchPlayerById(int id) {
		return fetchPlayerById(id, null);
	}

	public abstract PlayerModel fetchPlayerById(int id, int[] keys);
}

