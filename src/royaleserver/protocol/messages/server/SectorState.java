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

		DataStream preStream = new DataStream();
		preStream.put(Hex.toByteArray("00217f0b004b46ee86c0ea82820d017f7f7f7f000000000000"));

		preStream.putRrsInt32(8); // training arena
		preStream.putRrsInt32(3400);
		preStream.put(Hex.toByteArray("000000000000000000000000"));
		preStream.putRrsInt32(7);
		preStream.put(Hex.toByteArray("000000000000"));
		preStream.putRrsInt32(0);
		preStream.putRrsInt32(10);
		preStream.put(Hex.toByteArray("00000000000000000000"));
		preStream.putRrsInt32(4);

		preStream.putRrsLong(homeID);
		preStream.putRrsLong(homeID);
		preStream.putRrsLong(homeID);

		preStream.putString(username);
		preStream.putRrsInt32(arena); // Arena
		preStream.putRrsInt32(trophies);
		preStream.putRrsInt32(512);
		preStream.put(Hex.toByteArray("0000000000"));
		preStream.put(Hex.toByteArray("1f0000000000"));
		preStream.putRrsInt32(7);

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
		homeResources.encode(preStream);

		preStream.putRrsInt32(0);
		preStream.putRrsInt32(0);
		preStream.putRrsInt32(0);

		homeResources.resources.put(HomeResources.RESOURCE_HIGHEST_TROPHIES, trophies);
		homeResources.resources.put(HomeResources.RESOURCE_THREE_CROWNS_WIN, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_N11, 31);
		homeResources.resources.put(HomeResources.RESOURCE_MAX_CHALLENGE_WINS, 0);
		homeResources.resources.put(HomeResources.RESOURCE_UNK_N27, 0);
		homeResources.encode(preStream);

		// Unknown structure 2
		int lengthOfStruct_2 = 46;
		int lengthOfStruct_3 = 11;
		int lengthOfStruct_4 = 15;

		// # COUNT OF RESOURCES #
		preStream.putRrsInt32(lengthOfStruct_2 + lengthOfStruct_3 + lengthOfStruct_4);

		for (int i = 0; i < lengthOfStruct_2; i++) {
			preStream.putByte((byte) 26);
			preStream.putRrsInt32(i);
			preStream.putRrsInt32(0);
		}

		for (int i = 0; i < lengthOfStruct_3; i++) {
			preStream.putByte((byte) 27);
			preStream.putRrsInt32(i);
			preStream.putRrsInt32(0);
		}

		for (int i = 0; i < lengthOfStruct_4; i++) {
			preStream.putByte((byte) 28);
			preStream.putRrsInt32(i);
			preStream.putRrsInt32(0);
		}

		preStream.putRrsInt32(0);
		preStream.putRrsInt32(10);
		preStream.putRrsInt32(2);
		preStream.putRrsInt32(0);
		preStream.putRrsInt32(30751); // Gold?

		preStream.putString("BACK IN USSR"); // clan
		preStream.putRrsInt32(150);

		preStream.putRrsInt32(2035);
		preStream.putRrsInt32(1744);

		preStream.putRrsInt32(0);

		preStream.putRrsInt32(wins);
		preStream.putRrsInt32(looses);

		preStream.putRrsInt32(1);
		preStream.putRrsInt32(211);

		preStream.putRrsInt32(23);
		preStream.putRrsInt32(0);
		preStream.putRrsInt32(0);

		preStream.putRrsInt32(2);
		preStream.putRrsInt32(2);

		preStream.putRrsInt32(40);
		preStream.putRrsInt32(1);

		// Enemy id?
		preStream.putRrsInt32(-64); // null
		preStream.putRrsInt32(-64); // null

		preStream.putRrsInt32(0);

		preStream.putRrsLong(homeID);
		preStream.put(Hex.toByteArray("00000000000000000000"));

		// IsTrainer
		preStream.putRrsInt32(1);

		preStream.put(Hex.toByteArray("000000"));
		preStream.putRrsInt32(142);

		preStream.put(Hex.toByteArray("f27d0000"));

		preStream.putRrsInt32(6);
		preStream.putRrsInt32(6);

		// Trainer strategy?!
		int[] sixStruct_1 = new int[]{1, 1, 1, 1, 0, 0};
		int[] sixStruct_2 = new int[]{1, 0, 1, 0, 0, 1};

		for (int i = 0; i < 6; i++) {
			preStream.putRrsInt32(35);
			preStream.putRrsInt32(sixStruct_1[i]);
		}

		for (int i = 0; i < 6; i++)
			preStream.putRrsInt32(sixStruct_2[i]);

		for (int i = 0; i < 6; i++) {
			preStream.putRrsInt32(5);
			preStream.putRrsInt32(i);
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
			preStream.putRrsInt32(12); // level
			preStream.putRrsInt32(13); // Prefix
			preStream.putRrsInt32(coordinates[i][0]); // x
			preStream.putRrsInt32(coordinates[i][1]); // y
			preStream.put(Hex.toByteArray(stamps[i]));
			preStream.put(Hex.toByteArray("a40100000000020000000000000000"));
		}

		preStream.put(Hex.toByteArray("000504057c027f0404060007007f7f0000000500000000007f7f7f7f7f7f7f7f"));

		preStream.putRrsInt32(12); // my level
		preStream.putRrsInt32(13); // my Prefix
		preStream.putRrsInt32(9000); // x
		preStream.putRrsInt32(29000); // y
		preStream.put(Hex.toByteArray("0000c07c00"));
		preStream.put(Hex.toByteArray("a40100000000020000000000000000"));

		preStream.put(Hex.toByteArray("000504020201010403000107"));
		preStream.put(Hex.toByteArray("007f7f000000"));

		preStream.putRrsInt32(10); // start elixir
		preStream.put(Hex.toByteArray("00000000007f7f7f7f7f7f7f7f"));

		preStream.put(Hex.toByteArray("000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000"));

		preStream.putRrsInt32(3668); // Right Tower (my)
		preStream.putRrsInt32(3668); // Left Tower (enemy)

		preStream.putRrsInt32(3668); // Left Tower (my)
		preStream.putRrsInt32(3668); // Right Tower (enemy)

		preStream.putRrsInt32(5832); // enemy King Tower
		preStream.putRrsInt32(5832); // my King Tower

		for (int i = 0; i < 6; i++)
			preStream.put(Hex.toByteArray("00000000000000a401a401"));

		preStream.put(Hex.toByteArray("ff01"));

		GameDeck botDeck = new GameDeck();
		botDeck.encode(preStream);

		preStream.put(Hex.toByteArray("00"));
		preStream.put(Hex.toByteArray("fe03"));

		GameDeck myDeck = new GameDeck();
		myDeck.encode(preStream);

		preStream.put(Hex.toByteArray("0000"));

		preStream.putRrsInt32(5);
		preStream.putRrsInt32(6);
		preStream.putRrsInt32(2);
		preStream.putRrsInt32(2);
		preStream.putRrsInt32(4);
		preStream.putRrsInt32(2);
		preStream.putRrsInt32(1);
		preStream.putRrsInt32(2);

		preStream.put(Hex.toByteArray("00000000000000060901010000000000000000000000010000000000000000000000000c00000080e993c00b002a002b"));

		// Compress
		Deflater compressor = new Deflater();
		compressor.setInput(preStream.get());

		stream.put(Hex.toByteArray("01B2030000"));

		byte[] buf = new byte[1024];
		int length = 0;

		compressor.finish();
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			stream.put(buf, 0, count);
			length += count;
		}
		compressor.end();
	}
}
