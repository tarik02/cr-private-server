package royaleserver.database.model;

public class PlayerModel extends Model {
	public static final int KEY_ID = 0;
	public static final int KEY_NAME = 1;
	public static final int KEY_GOLD = 2;
	public static final int KEY_GEMS = 3;
	public static final int KEY_LEVEL_ID = 4;
	public static final int KEY_LEVEL_EXPERIENCE = 5;
	public static final int KEY_ARENA_ID = 6;
	public static final int KEY_ARENA_TROPHIES = 7;
	public static final int KEY_FAVOURITE_CARD_ID = 8;
	public static final int KEY_WINS = 9;
	public static final int KEY_LOOSES = 10;
	public static final int KEY_TREEE_CROWNS_WINS = 11;
	public static final int KEY_CLAN_ID = 12;
	public static final int KEY_CLAN_ROLE = 13;

	public PlayerModel() {}

	public PlayerModel(Object[] properties) {
		super(properties);
	}
}

