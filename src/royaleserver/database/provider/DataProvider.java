package royaleserver.database.provider;

import royaleserver.database.data.PlayerData;

public abstract class DataProvider {
	public abstract boolean fetchPlayerData(int id, PlayerData playerData);
}

