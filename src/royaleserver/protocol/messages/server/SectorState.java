package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.component.Deck;
import royaleserver.protocol.messages.component.GameDeck;
import royaleserver.protocol.messages.component.HomeResources;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;
import royaleserver.utils.SCID;

import java.util.zip.Deflater;

public class SectorState extends Message {

	public static final short ID = Info.SECTOR_STATE;

	public long homeID;
	public String username;

	public int place;
	public int arena;
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
	public SCID favouriteCard;

	public int challengeMaxWins;
	public int challengeCardsWon;
	public int tournamentPlays;
	public int tournamentCardsWon;

	public HomeResources homeResources;

	public SectorState() {
		super(ID);

		homeID = 0;
		username = "";

		place = 0;
		arena = 1;
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
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.put(Hex.toByteArray("00217f0b004b46ee86c0ea82820d017f7f7f7f000000000000"));

		stream.putRrsInt32(8); // training arena
		stream.putRrsInt32(3400);
		stream.put(Hex.toByteArray("000000000000000000000000"));
		stream.putRrsInt32(7);
		stream.put(Hex.toByteArray("000000000000"));
		stream.putRrsInt32(0);
		stream.putRrsInt32(10);
		stream.put(Hex.toByteArray("00000000000000000000"));
		stream.putRrsInt32(4);

		stream.putRrsLong(homeID);
		stream.putRrsLong(homeID);
		stream.putRrsLong(homeID);

		stream.putString(username);
		stream.putRrsInt32(arena); // Arena
		stream.putRrsInt32(trophies);
		stream.putRrsInt32(512);
		stream.put(Hex.toByteArray("0000000000"));
		stream.put(Hex.toByteArray("1f0000000000"));
		stream.putRrsInt32(7);

		homeResources.resources.put(HomeResources.RESOURCE_GOLD, gold);
		homeResources.resources.put(HomeResources.RESOURCE_CHALLENGE_WINS, 0);
		homeResources.resources.put(HomeResources.RESOURCE_CHEST_COINT, 3);
		homeResources.resources.put(HomeResources.RESOURCE_STAR_COUNT, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_0, 827);
		homeResources.resources.put(HomeResources.RESOURCE_REWARD_GOLD, 0);
		homeResources.resources.put(HomeResources.RESOURCE_REWARD_COUNT, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_1, 557);
		homeResources.resources.put(HomeResources.RESOURCE_TOURNAMENT_CARDS_WON, 759);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_4, 1307334552);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_5, 13);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_6, 0);
		homeResources.resources.put(HomeResources.RESOURCE_GAME_MODE, 72000009); // 72000009 - pvp with trainer?
		homeResources.encode(stream);

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		homeResources.resources.put(HomeResources.RESOURCE_HIGHEST_TROPHIES, trophies);
		homeResources.resources.put(HomeResources.RESOURCE_THREE_CROWNS_WIN, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_N11, 31);
		homeResources.resources.put(HomeResources.RESOURCE_MAX_CHALLENGE_WINS, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_N27, 0);
		homeResources.encode(stream);

		// Unknown structure 2
		int lengthOfStruct_2 = 46;
		int lengthOfStruct_3 = 11;
		int lengthOfStruct_4 = 15;

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
		stream.putRrsInt32(10);
		stream.putRrsInt32(2);
		stream.putRrsInt32(0);
		stream.putRrsInt32(30751); // Gold?

		stream.putString("BACK IN USSR"); // clan
		stream.putRrsInt32(150);

		stream.putRrsInt32(2035);
		stream.putRrsInt32(1744);

		stream.putRrsInt32(0);

		stream.putRrsInt32(wins);
		stream.putRrsInt32(looses);

		stream.putRrsInt32(1);
		stream.putRrsInt32(211);

		stream.putRrsInt32(23);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		stream.putRrsInt32(2);
		stream.putRrsInt32(2);

		stream.putRrsInt32(40);
		stream.putRrsInt32(1);

		// Enemy id?
		stream.putRrsInt32(-64); // null
		stream.putRrsInt32(-64); // null

		stream.putRrsInt32(0);

		stream.putRrsLong(homeID);
		stream.put(Hex.toByteArray("00000000000000000000"));

		// IsTrainer
		stream.putRrsInt32(1);

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
						{9000, 3000},
				};

		String[] stamps = new String[]
				{
						"0000c07c00",
						"0000800400",
						"0000c07c00",
						"0000800400",
						"0000800400"
				};

		for (int i = 0; i < coordinates.length; i++) {
			stream.putRrsInt32(12); // level
			stream.putRrsInt32(13); // Prefix
			stream.putRrsInt32(coordinates[i][0]); // x
			stream.putRrsInt32(coordinates[i][1]); // y
			stream.put(Hex.toByteArray(stamps[i]));
			stream.put(Hex.toByteArray("a40100000000020000000000000000"));
		}

		stream.put(Hex.toByteArray("000504057c027f0404060007007f7f0000000500000000007f7f7f7f7f7f7f7f"));

		stream.putRrsInt32(12); // my level
		stream.putRrsInt32(13); // my Prefix
		stream.putRrsInt32(9000); // x
		stream.putRrsInt32(29000); // y
		stream.put(Hex.toByteArray("0000c07c00"));
		stream.put(Hex.toByteArray("a40100000000020000000000000000"));

		stream.put(Hex.toByteArray("000504020201010403000107"));
		stream.put(Hex.toByteArray("007f7f000000"));

		stream.putRrsInt32(10); // start elixir
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

		stream.put(Hex.toByteArray("ff01"));

		GameDeck botDeck = new GameDeck();
		botDeck.encode(stream);

		stream.put(Hex.toByteArray("00"));
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
		stream.putRrsInt32(1);
		stream.putRrsInt32(2);

		stream.put(Hex.toByteArray("00000000000000060901010000000000000000000000010000000000000000000000000c00000080e993c00b002a002b"));

		stream.zlibCompress();
	}
}
