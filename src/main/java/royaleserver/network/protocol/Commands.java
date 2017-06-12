package royaleserver.network.protocol;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Commands {
	private Commands() {}

	// Server commands
	public static final short NAME_SET = 201;
	public static final short CLAN_LEAVE_OK = 205;
	public static final short CLAN_CREATE_OK = 206;
	public static final short CHEST_OPEN_OK = 210;

	// Client commands
	public static final short DECK_CHANGE_CARD = 500;
	public static final short DECK_CHANGE = 501;
	public static final short CHEST_OPEN = 503;
	public static final short CARD_UPGRADE = 504;
	public static final short CHEST_BUY = 516;
	public static final short FIGHT_START = 525;
	public static final short CHEST_CARD_NEXT = 526;
	public static final short CHALLENGE_BUY = 537;
	public static final short CHEST_DRAFT_CARD_SELECT = 553;
	public static final short CHEST_SEASON_REWARD_OPEN = 556;


	public static final Map<Short, String> commandsMap = createMap();

	private static Map<Short, String> createMap() {
		Map<Short, String> map = new HashMap<>();

		for (Field field : Commands.class.getFields()) {
			int modifiers = field.getModifiers();
			if (Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers) && field.getType() == short.class) {
				try {
					map.put(field.getShort(null), field.getName());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return Collections.unmodifiableMap(map);
	}
}
