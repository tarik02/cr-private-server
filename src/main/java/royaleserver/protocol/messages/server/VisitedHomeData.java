package royaleserver.protocol.messages.server;

import royaleserver.logic.Arena;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.component.Deck;
import royaleserver.protocol.messages.component.HomeResources;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;
import royaleserver.utils.SCID;

public class VisitedHomeData extends Message {

	public static final short ID = Info.VISITED_HOME_DATA;

	public boolean isMyProfile;

	public long homeID;
	public String username;

	public int place;
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
	public SCID favouriteCard;

	public int challengeMaxWins;
	public int challengeCardsWon;
	public int tournamentPlays;
	public int tournamentCardsWon;

	public HomeResources homeResources;

	public VisitedHomeData() {
		super(ID);

		isMyProfile = false;

		homeID = 0;
		username = "";

		place = 0;
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
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(250);
		stream.putRrsInt32(place);

		stream.put(Hex.toByteArray("ff")); // It seems that's always before deck
		deck.encode(stream);

		stream.putBLong(homeID);

		stream.putByte((byte)0);
		stream.putByte((byte)0);

		stream.putRrsLong(homeID);
		stream.putRrsLong(homeID);
		stream.putRrsLong(homeID);

		stream.putString(username);
		stream.putRrsInt32(0); // name changes count
		stream.putByte((byte)arena.getIndex());

		stream.putRrsInt32(trophies);
		stream.putRrsInt32(657); // unk_6

		stream.putRrsInt32(0); // trophies legendary
		stream.putRrsInt32(0); // trophies s h

		stream.putRrsInt32(0); // always30

		stream.putRrsInt32(0); // BestSeasonLeaderboardNr
		stream.putRrsInt32(0); // BestSeasonTrophies

		stream.putRrsInt32(0); // unk_6_b
		stream.putRrsInt32(31); // always30_2

		stream.putRrsInt32(0); // PreviousSeasonLeaderboardNr
		stream.putRrsInt32(0); // PreviousSeasonTrophies

		stream.putRrsInt32(highestTrophies); // HighestTrophies

		stream.putRrsInt32(0); //unk_7 = p.readRRSInt32()
		stream.putRrsInt32(0); //unk_8 = p.readRRSInt32()
		stream.putRrsInt32(7); //always_7 = p.readRRSInt32()

		homeResources.resources.put(HomeResources.RESOURCE_GOLD, gold);
		homeResources.resources.put(HomeResources.RESOURCE_FREE_GOLD, gold);
		homeResources.resources.put(HomeResources.RESOURCE_THREE_CROWNS_WIN, threeCrownsWin);
		homeResources.resources.put(HomeResources.RESOURCE_CARDS_FOUND, cardsFound);
		homeResources.resources.put(HomeResources.RESOURCE_CARDS_GIVEN, cardsGiven);
		homeResources.resources.put(HomeResources.RESOURCE_HIGHEST_TROPHIES, highestTrophies);
		homeResources.resources.put(HomeResources.RESOURCE_CHALLENGE_CARDS_WON, challengeCardsWon);
		homeResources.resources.put(HomeResources.RESOURCE_CHALLENGE_CARDS_WON2, challengeCardsWon);
		homeResources.resources.put(HomeResources.RESOURCE_MAX_CHALLENGE_WINS, challengeMaxWins);
		homeResources.resources.put(HomeResources.RESOURCE_FAVOURITE_CARD, (int)(favouriteCard == null ? 0 : favouriteCard.getValue()));
		homeResources.encode(stream);

		stream.putRrsInt32(0);

		int[] statItems = new int[]{
			26, cardsGiven, cardsGiven, cardsGiven,
			10, 10, 10,
			cardsFound, cardsFound, cardsFound,
			1, tournamentCardsWon, tournamentCardsWon, tournamentCardsWon,
			1, 1, 1, 726, challengeMaxWins, challengeMaxWins, challengeMaxWins,
			121, 121, 121, 14, 14, 14,
			cardsGiven, cardsGiven, cardsGiven
		};

		stream.putRrsInt32(statItems.length);
		for (int i = 0; i < statItems.length; i++) {
			stream.putByte((byte)60);
			stream.putRrsInt32(i);
			stream.putRrsInt32(statItems[i]);
		}

		// Unknown structure
		int lengthOfStruct = 28;

		stream.putRrsInt32(lengthOfStruct);

		for (int i = 0; i < lengthOfStruct; i++) {
			stream.putByte((byte)60);
			stream.putRrsInt32(i);
			stream.putRrsInt32(1);
		}

		homeResources.encode(stream);

		// Unknown structure 2
		int lengthOfStruct_2 = 46;
		int lengthOfStruct_3 = 11;
		int lengthOfStruct_4 = 15;

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

		stream.putRrsInt32(0); // 0

		stream.putRrsInt32(gems); // Gems
		stream.putRrsInt32(gems); // Free gems

		stream.putRrsInt32(levelExperience);
		stream.putRrsInt32(level);

		stream.putBoolean(isMyProfile);

		/*
		 if (!playerInfo[2].equals("")) {
		 // ClanData
		 ClanWorker clanWorker = new ClanWorker(this.player);
		 String[] clanData = clanWorker.getClanDataU("ClanID", playerInfo[2]);

		 if (clanData != null) {
		 // hasName|hasClan // 9 - hasClan and username
		 stream.putRrsInt32(9);

		 // ClanTAG
		 stream.putRrsLong(Long.parseLong(clanData[0]));

		 // clan info: 1) name 2) badge
		 stream.putString(clanData[1]);
		 stream.putRrsInt32(Integer.parseInt(clanData[2]) + 1); // why +1?!

		 // role
		 stream.putRrsInt32(4);
		 }

		 } else {*/
		stream.putRrsInt32(1);
		//}

		// # END CLAN INFO # //
		// Matches played (global/tournament/challenge)?
		stream.putRrsInt32(0); // global?
		stream.putRrsInt32(0); // tournament
		stream.putRrsInt32(0); // unk

		stream.putRrsInt32(wins);
		stream.putRrsInt32(looses);

		// Unknown
		stream.putRrsInt32(1);

		// ?Crowns earned => ladder?
		stream.putRrsInt32(0);

		// Tutorial Step (06 = setName , 08 = lastDone)
		stream.putRrsInt32(8);

		// Tournament?
		stream.putRrsInt32(0);

		// Unexplored data
		stream.put(Hex.toByteArray("0419bbb1bb0519bbb1bb0519bbb1bb0500000004587365740008ae3480080000000000001f000000000007120501ade8030502b0070503000504000505ade803050cbb0c050d00050e00050fad080510a5030511b2030512a7030513aa030516b70b051998dee2de09051a0d051c00051d8e88d544001e3c001a3c0192e7013c0292e7013c0392e7013c040a3c050a3c060a3c0785013c0885013c0985013c0a013c0b8e0c3c0c8e0c3c0d8e0c3c0e013c0f013c10013c11960b3c120b3c130b3c140b3c15b9013c16b9013c17b9013c180e3c190e3c1a0e3c1b92e7013c1c92e7013c1d92e701173c00013c01013c02013c03013c04013c05013c06013c07013c08013c09013c0a013c0e013c11013c12013c13013c15013c16013c17013c18013c19013c1a013c1b013c1c01090506b13505078a0305088501050988f3df19050a92e701050b1f05140b05158d1b051b0a88011a00001a01001a02001a03001a04001a05001a06001a07001a08001a09001a0a001a0b001a0c001a0d001a0e001a0f001a10001a11001a12001a13001a14001a15001a16001a17001a18001a19001a1a001a1b001a1c001a1d2a1a1e001a1f001a20001a21001a22001a23001a24001a25001a26001a27001a28001a29001a2aab021a2b001a2d001a2e001b00001b01001b02001b03001b04001b05001b06001b07001b08001b09001b0a001c00001c01001c02001c03001c04001c05001c06001c07001c08001c09001c0a001c0b001c0c001c0d001c100900a101a1018689020aa84109009fe0030000000c4241434b20494e2055535352920204b31f8c1b00920d970d019303170000"));
	}
}