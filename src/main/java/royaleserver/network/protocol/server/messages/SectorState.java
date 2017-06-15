package royaleserver.network.protocol.server.messages;

import royaleserver.logic.Arena;
import royaleserver.logic.Card;
import royaleserver.logic.ExpLevel;
import royaleserver.logic.GameMode;
import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.Deck;
import royaleserver.network.protocol.server.components.GameDeck;
import royaleserver.network.protocol.server.components.HomeResources;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public final class SectorState extends ServerMessage {
	public static final short ID = Messages.SECTOR_STATE;

	public long homeID;
	public String username;

	public Arena arena;
	public int trophies;
	public int highestTrophies;

	public ExpLevel level;
	public int gold;

	public int wins;
	public int threeCrownsWin;
	public int looses;

	public Deck currentDeck;
	public int cardsFound;
	public int cardsGiven;
	public Card favouriteCard;

	public int challengeMaxWins;
	public int challengeCardsWon;
	public int tournamentCardsWon;

	public HomeResources homeResources;

	public SectorState() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new SectorState();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putByte((byte)0);
		stream.putByte((byte)33);

		stream.putRrsInt32(-64); // timestamp

		stream.putByte((byte)11);
		stream.putByte((byte)0);

		stream.put(Hex.toByteArray("3d957263d6d0acbe"));

		stream.putByte((byte)5);
		stream.putByte((byte)1);

		// trainer ids = null = -64 (varint) = 127 (byte)
		stream.putRrsInt32(-64);
		stream.putRrsInt32(-64);

		stream.putRrsInt32(-64);
		stream.putRrsInt32(-64);

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		stream.putString("");

		stream.putRrsInt32(1);
		stream.putRrsInt32(3400);

		stream.put(Hex.toByteArray("00000000000000000000000000")); // Rank , Trophies , LegendaryTrophies

		stream.putRrsInt32(8);
		stream.put(Hex.toByteArray("0000000000000000"));

		stream.putRrsInt32(10);
		stream.put(Hex.toByteArray("0000000000000000000000"));

		stream.putRrsInt32(1);
		stream.putRrsInt32(2);

		// my ids
		stream.putRrsLong(homeID);
		stream.putRrsLong(homeID);
		stream.putRrsLong(homeID);

		stream.putString("Xset");
		stream.putRrsInt32(8); // enemy arena?
		stream.putRrsInt32(3335);
		stream.putRrsInt32(595);
		stream.putRrsInt32(3299);
		stream.put(Hex.toByteArray("0000000000"));
		stream.putRrsInt32(32);
		stream.put(Hex.toByteArray("0000000000"));
		stream.putRrsInt32(8); // always 7 (changed in new version. Maybe it's a version?)

		// my resources
		HomeResources homeResources = new HomeResources();
		homeResources.resources.put(HomeResources.RESOURCE_GOLD, 10000);
		homeResources.resources.put(HomeResources.RESOURCE_CHALLENGE_WINS, 0);
		homeResources.resources.put(HomeResources.RESOURCE_CHEST_COINT, 3);
		homeResources.resources.put(HomeResources.RESOURCE_STAR_COUNT, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_0, 827);
		homeResources.resources.put(HomeResources.RESOURCE_REWARD_GOLD, 0);
		homeResources.resources.put(HomeResources.RESOURCE_REWARD_COUNT, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_1, 557);
		homeResources.resources.put(HomeResources.RESOURCE_TOURNAMENT_CARDS_WON, 759);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_6, 0);
		homeResources.resources.put(HomeResources.RESOURCE_GAME_MODE, 72000009); // 72000009 - pvp with trainer?
		homeResources.encode(stream);

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		homeResources = new HomeResources();
		homeResources.resources.put(HomeResources.RESOURCE_HIGHEST_TROPHIES, trophies);
		homeResources.resources.put(HomeResources.RESOURCE_THREE_CROWNS_WIN, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_N11, 31);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_N27, 0);
		homeResources.encode(stream);

		// Unknown structure 2
		int lengthOfStruct_2 = 0;
		int lengthOfStruct_3 = 0;
		int lengthOfStruct_4 = 0;

		// # COUNT OF RESOURCES #
		stream.putRrsInt32(lengthOfStruct_2 + lengthOfStruct_3 + lengthOfStruct_4);

		for (int i = 0; i < lengthOfStruct_2; i++) {
			stream.putByte((byte)26);
			stream.putRrsInt32(i);
			stream.putRrsInt32(0);
		}

		for (int i = 0; i < lengthOfStruct_3; i++) {
			stream.putByte((byte)27);
			stream.putRrsInt32(i);
			stream.putRrsInt32(0);
		}

		for (int i = 0; i < lengthOfStruct_4; i++) {
			stream.putByte((byte)28);
			stream.putRrsInt32(i);
			stream.putRrsInt32(0);
		}

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(10);

		// id of clan?
		stream.putRrsInt32(2);
		stream.putRrsInt32(0);
		stream.putRrsInt32(30751);

		stream.putString("SAMPLE CLAN");

		stream.putRrsInt32(146);
		stream.putRrsInt32(2236);
		stream.putRrsInt32(1896);
		stream.putRrsInt32(657);
		stream.putRrsInt32(872);
		stream.putRrsInt32(878);

		stream.putByte((byte)126);
		stream.putRrsInt32(211);
		stream.putRrsInt32(24);
		stream.putRrsInt32(0);

		stream.putRrsInt32(1);
		stream.putBInt(65000001);

		stream.putRrsInt32(6);
		stream.putRrsInt32(1);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		stream.putRrsInt32(2);
		stream.putRrsInt32(2);

		stream.putRrsInt32(39);
		stream.putRrsInt32(1);

		// enemy ids
		stream.putRrsInt32(-64); // null
		stream.putRrsInt32(-64); // null

		stream.putRrsInt32(0);

		// my id
		stream.putRrsLong(homeID);
		stream.put(Hex.toByteArray("00000000000000000000"));

		// IsTrainer
		stream.putRrsInt32(1);

		stream.put(Hex.toByteArray("000000"));

		stream.putRrsInt32(142);
		stream.put(Hex.toByteArray("f27d"));

		stream.put(Hex.toByteArray("0000"));

		stream.putRrsInt32(6);
		stream.putRrsInt32(6);

		// Trainer strategy?!
		int[] sixStruct_1 = new int[]{1, 1, 1, 1, 0, 0};
		int[] sixStruct_2 = new int[]{1, 0, 1, 0, 0, 1};

		for (int i = 0; i < 6; i++) {
			stream.putRrsInt32(35);
			stream.putRrsInt32(sixStruct_1[i]);
		}

		for (int i = 0; i < 6; i++)
			stream.putRrsInt32(sixStruct_2[i]);

		for (int i = 0; i < 6; i++) {
			stream.putRrsInt32(5);
			stream.putRrsInt32(i);
		}

		// Coordinates of towers
		int[][] coordinates = new int[][]{
				{14500, 25500},
				{3500, 6500},
				{3500, 25500},
				{14500, 6500},
		};

		String[] StringStamps = new String[]{
				"c07c0002",
				"80040001",
				"c07c0001",
				"80040002",
		};

		for (int i = 0; i < coordinates.length; i++) {
			stream.putRrsInt32(12); // level
			stream.putRrsInt32(13); // Prefix
			stream.putRrsInt32(coordinates[i][0]); // x
			stream.putRrsInt32(coordinates[i][1]); // y
			stream.putBInt(32512);
			stream.put(Hex.toByteArray(StringStamps[i]));
			stream.put(Hex.toByteArray("000000000000"));
		}

		// enemy profile | elixir
		stream.putRrsInt32(12); // level
		stream.putRrsInt32(13); // Prefix
		stream.putRrsInt32(9000); // x
		stream.putRrsInt32(3000); // y
		stream.putBInt(32512);
		stream.put(Hex.toByteArray("80040000")); // king tower  (end 00 byte)
		stream.put(Hex.toByteArray("000000000000"));
		stream.put(Hex.toByteArray("0005040004037a0405030602007f7f0000000500"));
		stream.put(Hex.toByteArray("000000007f7f7f7f7f7f7f7f"));

		stream.putRrsInt32(0);

		// my profile | elixir
		stream.putRrsInt32(12); // level
		stream.putRrsInt32(13); // Prefix
		stream.putRrsInt32(9000); // x
		stream.putRrsInt32(29000); // y
		stream.putBInt(32512);
		stream.put(Hex.toByteArray("c07c0000")); // king tower  (end 00 byte)
		stream.put(Hex.toByteArray("000000000000"));
		stream.put(Hex.toByteArray("0005040002057e0404030106007f7f0000000500"));
		stream.put(Hex.toByteArray("000000007f7f7f7f7f7f7f7f"));

		stream.putRrsInt32(0);

		stream.put(Hex.toByteArray("00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));

		stream.putRrsInt32(0);
		stream.putRrsInt32(3668); // Right Tower (my)

		stream.putRrsInt32(0);
		stream.putRrsInt32(3668); // Left Tower (enemy)

		stream.putRrsInt32(0);
		stream.putRrsInt32(3668); // Left Tower (my)

		stream.putRrsInt32(0);
		stream.putRrsInt32(3668); // Right Tower (enemy)

		stream.putRrsInt32(0);
		stream.putRrsInt32(5832); // enemy King Tower

		stream.putRrsInt32(0);
		stream.putRrsInt32(5832); // my King Tower

		stream.putRrsInt32(0);

		for (int i = 0; i < 6; i++)
			stream.put(Hex.toByteArray("00000000000000a401a401"));

		stream.put(Hex.toByteArray("ff01"));

		GameDeck botDeck = new GameDeck();
		botDeck.encode(stream);

		stream.put(Hex.toByteArray("00"));

		stream.put(Hex.toByteArray("fe03"));

		for (royaleserver.network.protocol.server.components.Card card : currentDeck.cards) {
			stream.putRrsInt32(card.card.getIndex());
			stream.putRrsInt32(card.level - 1);
		}

		stream.put(Hex.toByteArray("0000"));

		stream.putRrsInt32(5);
		stream.putRrsInt32(6);

		stream.putRrsInt32(2);
		stream.putRrsInt32(2);

		stream.putRrsInt32(4);
		stream.putRrsInt32(2);

		// unexplored data. always looks like this:
		stream.put(Hex.toByteArray("010300000000000000060100000900000c000000ac85fcd003002a002b"));

		// make compression
		stream.zlibCompress();
	}
}
