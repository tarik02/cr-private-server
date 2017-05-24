package royaleserver.database;

import royaleserver.database.service.PlayerService;

/**
 * A class that contains all the services.
 */
public final class DataServices {
	public final PlayerService playerService;

	public DataServices(PlayerService playerService) {
		this.playerService = playerService;
	}
}
