package royaleserver.network.protocol.server.messages;

import royaleserver.logic.Arena;
import royaleserver.logic.Card;
import royaleserver.logic.GameMode;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.protocol.messages.component.Deck;
import royaleserver.protocol.messages.component.GameDeck;
import royaleserver.network.protocol.server.components.HomeResources;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public final class SectorState extends ServerMessage {
	public static final short ID = Messages.SECTOR_STATE;

	public long homeID;
	public String username;

	public GameMode gameMode;

	public Arena arena;
	public int trophies;
	public int highestTrophies;

	public int level;
	public int levelExperience;

	public int gold;
	public int gems;

	public int wins;
	public int threeCrownsWin;
	public int looses;

	public Deck deck;
	public int cardsFound;
	public int cardsGiven;
	public Card favouriteCard;

	public int challengeMaxWins;
	public int challengeCardsWon;
	public int tournamentPlays;
	public int tournamentCardsWon;

	public HomeResources homeResources;
	public int isTrainer;

	public SectorState() {
		super(ID);

		homeID = 0;
		username = "";

		gameMode = GameMode.by("Training");

		arena = Arena.by("TrainingCamp");
		trophies = 0;
		highestTrophies = 0;

		level = 0;
		levelExperience = 0;

		gold = 0;
		gems = 0;

		wins = 0;
		looses = 0;

		deck = new Deck();
		cardsFound = 0;
		cardsGiven = 0;
		favouriteCard = null;

		challengeMaxWins = 0;
		challengeCardsWon = 0;
		tournamentPlays = 0;
		tournamentCardsWon = 0;

		homeResources = new HomeResources();
		isTrainer = 1;
	}

	@Override
	public void encode(DataStream stream) {
		stream.putByte((byte)0);
		stream.putByte((byte)33);

		stream.putRrsInt32((int)System.currentTimeMillis()); // timestamp

		stream.putByte((byte)11);
		stream.putByte((byte)0);

		stream.put(Hex.toByteArray("4b46ee86c0ea8282"));

		stream.putByte((byte)4);
		stream.putByte((byte)1);

		if (isTrainer == 1) {
			// trainer ids = null = -64 (varint) = 127 (byte)
			stream.putRrsInt32(-64);
			stream.putRrsInt32(-64);

			stream.putRrsInt32(-64);
			stream.putRrsInt32(-64);

			stream.putRrsInt32(0);
			stream.putRrsInt32(0);

			stream.putString("");

			stream.putRrsInt32(8);
			stream.putRrsInt32(3400);

			stream.put(Hex.toByteArray("000000000000000000000000")); // Rank , Trophies , LegendaryTrophies

			stream.putRrsInt32(7);
			stream.put(Hex.toByteArray("000000000000"));

			// resource count
			stream.putRrsInt32(0);
			stream.putRrsInt32(10);
			stream.put(Hex.toByteArray("00000000000000000000"));
			stream.putRrsInt32(4);
		} else {
			// enemy ids
			stream.putRrsLong(90199707829L);
			stream.putRrsLong(90199707829L);
			stream.putRrsLong(90199707829L);

			// enemy name
			stream.putString("Pashka");

			stream.putRrsInt32(arena.getIndex()); // enemy arena?
			stream.putRrsInt32(3400); // enemy trophies?
			stream.put(Hex.toByteArray("8c0300000000001f0000000000"));
			stream.putRrsInt32(7); // always 7

			// Enemy resources
			homeResources.resources.put(HomeResources.RESOURCE_GOLD, gold);
			homeResources.resources.put(HomeResources.RESOURCE_CHALLENGE_WINS, 0);
			homeResources.resources.put(HomeResources.RESOURCE_CHEST_COINT, 3);
			homeResources.resources.put(HomeResources.RESOURCE_STAR_COUNT, 0);
			homeResources.resources.put(HomeResources.RESOURCE_UNK_0, 827);
			homeResources.resources.put(HomeResources.RESOURCE_REWARD_GOLD, 0);
			homeResources.resources.put(HomeResources.RESOURCE_REWARD_COUNT, 0);
			homeResources.resources.put(HomeResources.RESOURCE_UNK_1, 557);
			homeResources.resources.put(HomeResources.RESOURCE_TOURNAMENT_CARDS_WON, 759);
			homeResources.resources.put(HomeResources.RESOURCE_UNK_6, 0);
			homeResources.resources.put(HomeResources.RESOURCE_GAME_MODE, (int)gameMode.getScid().getValue());
			homeResources.encode(stream);

			stream.putRrsInt32(0);
			stream.putRrsInt32(0);
			stream.putRrsInt32(0);

			homeResources.resources.put(HomeResources.RESOURCE_HIGHEST_TROPHIES, trophies);
			homeResources.resources.put(HomeResources.RESOURCE_THREE_CROWNS_WIN, 0);
			homeResources.resources.put(HomeResources.RESOURCE_UNK_N11, 31);
			homeResources.resources.put(HomeResources.RESOURCE_UNK_N27, 0);
			homeResources.encode(stream);

			// Unknown structure 2
			int lengthOfStruct_2_1 = 33;
			int lengthOfStruct_3_1 = 7;
			int lengthOfStruct_4_1 = 11;

			// # COUNT OF RESOURCES #
			stream.putRrsInt32(lengthOfStruct_2_1 + lengthOfStruct_3_1 + lengthOfStruct_4_1);

			for (int i = 0; i < lengthOfStruct_2_1; i++) {
				stream.putByte((byte)26);
				stream.putRrsInt32(i);
				stream.putRrsInt32(0);
			}

			for (int i = 0; i < lengthOfStruct_3_1; i++) {
				stream.putByte((byte)27);
				stream.putRrsInt32(i);
				stream.putRrsInt32(0);
			}

			for (int i = 0; i < lengthOfStruct_4_1; i++) {
				stream.putByte((byte)28);
				stream.putRrsInt32(i);
				stream.putRrsInt32(0);
			}

			stream.putRrsInt32(0);

			// clan information enemy
			stream.putRrsInt32(10); // ? hasClan
			stream.putRrsInt32(2); // ?
			stream.putRrsInt32(48);
			stream.putRrsInt32(385234);
			stream.putString("RoyaleLeaks");
			stream.putRrsInt32(150); // badge
			stream.putRrsInt32(1877); // ?
			stream.putRrsInt32(18);
			stream.putRrsInt32(0);

			// wins
			stream.putRrsInt32(792);
			stream.putRrsInt32(822);

			stream.putRrsInt32(2);
			stream.putRrsInt32(15);

			stream.putRrsInt32(1);
			stream.putRrsInt32(0);
			stream.putRrsInt32(4);
		}

		// my ids
		stream.putRrsLong(homeID);
		stream.putRrsLong(homeID);
		stream.putRrsLong(homeID);

		stream.putString(username);
		stream.putRrsInt32(arena.getIndex()); // enemy arena?
		stream.putRrsInt32(trophies);
		stream.putRrsInt32(512);
		stream.put(Hex.toByteArray("0000000000"));
		stream.put(Hex.toByteArray("1f0000000000"));
		stream.putRrsInt32(7); // always 7

		// my resources
		homeResources.resources.put(HomeResources.RESOURCE_GOLD, gold);
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

		homeResources.resources.put(HomeResources.RESOURCE_HIGHEST_TROPHIES, trophies);
		homeResources.resources.put(HomeResources.RESOURCE_THREE_CROWNS_WIN, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_N11, 31);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_N27, 0);
		homeResources.encode(stream);

		// Unknown structure 2
		int lengthOfStruct_2 = 33;
		int lengthOfStruct_3 = 7;
		int lengthOfStruct_4 = 11;

		// # COUNT OF RESOURCES #
		stream.putRrsInt32(lengthOfStruct_2 + lengthOfStruct_3 + lengthOfStruct_4);

		for (int i = 0; i < lengthOfStruct_2; i++) {
			stream.putByte((byte) 26);
			stream.putRrsInt32(i);
			stream.putRrsInt32(0);
		}

		for (int i = 0; i < lengthOfStruct_3; i++) {
			stream.putByte((byte) 27);
			stream.putRrsInt32(i);
			stream.putRrsInt32(0);
		}

		for (int i = 0; i < lengthOfStruct_4; i++) {
			stream.putByte((byte) 28);
			stream.putRrsInt32(i);
			stream.putRrsInt32(0);
		}

		stream.putRrsInt32(0);
		stream.put(Hex.toByteArray("0700910600009603a1027f170100000b"));
		stream.putRrsInt32(2);
		stream.putRrsInt32(0);
		stream.putRrsInt32(10);

		// Enemy ids
		if (isTrainer == 1) {
			stream.putRrsInt32(-64); // null
			stream.putRrsInt32(-64); // null
		} else {
			stream.putRrsLong(90199707829L);
		}

		stream.putRrsInt32(0);
		stream.putRrsLong(homeID);

		stream.put(Hex.toByteArray("00000000000000000000"));

		// IsTrainer
		stream.putRrsInt32(isTrainer);

		stream.put(Hex.toByteArray("000000"));
		stream.putRrsInt32(142);

		stream.put(Hex.toByteArray("f27d0000"));

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
		int[][] coordinates = new int[][]
				{
						{14500, 25500},
						{3500, 6500},
						{3500, 25500},
						{14500, 6500},
				};

		String[] stamps = new String[]
				{
						"0000c07c00",
						"0000800400",
						"0000c07c00",
						"0000800400",
				};

		for (int i = 0; i < coordinates.length; i++) {
			stream.putRrsInt32(12); // level
			stream.putRrsInt32(13); // Prefix
			stream.putRrsInt32(coordinates[i][0]); // x
			stream.putRrsInt32(coordinates[i][1]); // y
			stream.put(Hex.toByteArray(stamps[i]));
			stream.put(Hex.toByteArray("a40100000000000000000000000000"));
		}

		// enemy profile | elixir
		stream.putRrsInt32(12); // level
		stream.putRrsInt32(13); // Prefix
		stream.putRrsInt32(9000); // x
		stream.putRrsInt32(3000); // y
		stream.put(Hex.toByteArray("0000800400")); // stamp
		stream.put(Hex.toByteArray("a40100000000020000000000000000"));
		stream.put(Hex.toByteArray("00040000"));
		//stream.put(Hex.toByteArray("000504057c027f0404060007007f7f0000000500000000007f7f7f7f7f7f7f7f"));
		stream.putRrsInt32(5);
		stream.put(Hex.toByteArray("00000000007f7f7f7f7f7f7f7f"));

		// my profile | elixir
		stream.putRrsInt32(12); // level
		stream.putRrsInt32(13); // Prefix
		stream.putRrsInt32(9000); // x
		stream.putRrsInt32(29000); // y
		stream.put(Hex.toByteArray("0000c07c00")); // stamp
		stream.put(Hex.toByteArray("a40100000000020000000000000000"));
		stream.put(Hex.toByteArray("000504020201010403000107"));
		stream.put(Hex.toByteArray("007f7f000000"));
		stream.putRrsInt32(5);
		stream.put(Hex.toByteArray("00000000007f7f7f7f7f7f7f7f"));

		stream.put(Hex.toByteArray("000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));

		stream.putRrsInt32(3668); // Right Tower (my)
		stream.putRrsInt32(3668); // Left Tower (enemy)

		stream.putRrsInt32(3668); // Left Tower (my)
		stream.putRrsInt32(3668); // Right Tower (enemy)

		stream.putRrsInt32(5832); // enemy King Tower
		stream.putRrsInt32(5832); // my King Tower

		for (int i = 0; i < 6; i++)
			stream.put(Hex.toByteArray("00000000000000a401a401"));

		if (isTrainer == 1) {
			stream.put(Hex.toByteArray("ff01"));

			GameDeck botDeck = new GameDeck();
			botDeck.encode(stream);

			stream.put(Hex.toByteArray("00"));
		}

		stream.put(Hex.toByteArray("fe03"));

		GameDeck myDeck = new GameDeck();
		myDeck.encode(stream);

		stream.put(Hex.toByteArray("0000"));

		stream.putRrsInt32(5);
		stream.putRrsInt32(6);

		stream.putRrsInt32(2);
		stream.putRrsInt32(2);

		stream.putRrsInt32(4);
		stream.putRrsInt32(2);

		// battle time?
		stream.putRrsInt32(1); // double elixir = 1 min
		stream.putRrsInt32(2); // battle time = 3 min

		// unexplored data. always looks like this:
		stream.put(Hex.toByteArray("00000000000000060901010000000000000000000000010000000000000000000000000c00000080e993c00b002a002b"));

		// make compression
		stream.zlibCompress();
	}
}
