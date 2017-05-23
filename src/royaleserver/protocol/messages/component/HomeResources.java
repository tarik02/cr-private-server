package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;
import java.util.Map;
import java.util.HashMap;

public class HomeResources extends Component {
	public static final int RESOURCE_GOLD = 1;
	public static final int RESOURCE_CHALLENGE_WINS = 2; // challenge wins (not max!)
	public static final int RESOURCE_CHEST_COINT = 3;
	public static final int RESOURCE_STAR_COUNT = 4; // ???
	public static final int RESOURCE_FREE_GOLD = 5; // ???
	public static final int RESOURCE_HIGHEST_TROPHIES = 6;
	public static final int RESOURCE_THREE_CROWNS_WIN = 7;
	public static final int RESOURCE_CARDS_FOUND = 8;
	public static final int RESOURCE_FAVOURITE_CARD = 9; // SCID?
	public static final int RESOURCE_CARDS_GIVEN = 10;
	public static final int RESOURCE_UNK_N11 = 11;
	public static final int RESOURCE_UNK_0 = 12;
	public static final int RESOURCE_REWARD_GOLD = 13;
	public static final int RESOURCE_REWARD_COUNT = 14;
	public static final int RESOURCE_UNK_1 = 15; // Wins?
	public static final int RESOURCE_CARD_RED_BADGE = 16; // Or SHOP_DAY_COUNT. WHAT?
	public static final int RESOURCE_CHALLENGE_CARDS_WON = 17;
	public static final int RESOURCE_UNK_2 = 18;
	public static final int RESOURCE_UNK_3 = 19;
	public static final int RESOURCE_MAX_CHALLENGE_WINS = 20; // Challenge max wins
	public static final int RESOURCE_CHALLENGE_CARDS_WON2 = 21; // Repeat?
	public static final int RESOURCE_TOURNAMENT_CARDS_WON = 22;
	public static final int RESOURCE_UNK_4 = 25;
	public static final int RESOURCE_UNK_5 = 26;
	public static final int RESOURCE_UNK_N27 = 27;
	public static final int RESOURCE_UNK_6 = 28;
	public static final int RESOURCE_GAME_MODE = 29;

	public Map<Integer, Integer> resources;

	public HomeResources() {
		resources = new HashMap<>();
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(resources.size());
		for (Map.Entry<Integer, Integer> entry : resources.entrySet()) {
			stream.putByte((byte)5);
			stream.putRrsInt32(entry.getKey());
			stream.putRrsInt32(entry.getValue());
		}

		resources.clear();
	}
}
