package royaleserver.database.provider;

import royaleserver.database.data.PlayerData;

public abstract class DataProvider {
	public abstract boolean applyMigration(String name);

	public abstract int fetchVersion();
	public abstract void updateVersion(int version);

	public abstract boolean fetchPlayerData(int id, PlayerData playerData);
}

