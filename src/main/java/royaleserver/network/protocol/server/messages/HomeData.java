package royaleserver.network.protocol.server.messages;

import royaleserver.logic.Arena;
import royaleserver.logic.Card;
import royaleserver.logic.ExpLevel;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.HomeResources;
import royaleserver.network.protocol.server.components.PlayerClan;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public abstract class HomeData extends ServerMessage {
	public boolean isMyProfile;

	public long homeId;
	public String name;
	public int nameChangesCount;

	public PlayerClan clan;

	public int gold;
	public int gems, freeGems;

	public ExpLevel level, lastLevel;
	public int levelExperience;

	public Arena arena, lastArena;
	public int trophies;
	public int highestTrophies;
	public int legendaryTrophies;

	// Stats
	public int wins, threeCrownsWin, looses;
	public int cardsGiven, cardsFound;
	public int tournamentCardsWon;
	public int challengeCardsWon, challengeMaxWins;
	public Card favouriteCard;

	protected HomeData(short id) {
		super(id);
	}

	public void encode(DataStream stream) {

		stream.putRrsLong(homeId);
		stream.putRrsLong(homeId);
		stream.putRrsLong(homeId);

		if (name == null) {
			stream.putString(""); // Username is not set
		} else {
			stream.putString(name);
		}
		stream.putRrsInt32(nameChangesCount);

		stream.putRrsInt32(10);

		stream.putRrsInt32(trophies);
		stream.putRrsInt32(240); // unk_6 0
		stream.putRrsInt32(highestTrophies); // highest trophies?

		stream.putRrsInt32(0);
		stream.putRrsInt32(0); // always30
		stream.putRrsInt32(0); // BestSeasonLeaderboardNr
		stream.putRrsInt32(0); // BestSeasonTrophies
		stream.putRrsInt32(0); // unk_6_b
		stream.putRrsInt32(0); // always30_2
		stream.putRrsInt32(32); // PreviousSeasonLeaderboardNr
		stream.putRrsInt32(0); // PreviousSeasonTrophies
		stream.putRrsInt32(0); // PreviousSeasonTrophies
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(8); //always_7 = p.readRRSInt32()

		HomeResources homeResources = new HomeResources();
		homeResources.resources.put(HomeResources.RESOURCE_GOLD, gold);
		homeResources.resources.put(HomeResources.RESOURCE_FREE_GOLD, gold);
		homeResources.resources.put(HomeResources.RESOURCE_THREE_CROWNS_WIN, threeCrownsWin);
		homeResources.resources.put(HomeResources.RESOURCE_CARDS_FOUND, cardsFound);
		homeResources.resources.put(HomeResources.RESOURCE_CARDS_GIVEN, cardsGiven);
		homeResources.resources.put(HomeResources.RESOURCE_HIGHEST_TROPHIES, highestTrophies);
		homeResources.resources.put(HomeResources.RESOURCE_CHALLENGE_CARDS_WON, challengeCardsWon);
		homeResources.resources.put(HomeResources.RESOURCE_CHALLENGE_CARDS_WON2, challengeCardsWon);
		homeResources.resources.put(HomeResources.RESOURCE_MAX_CHALLENGE_WINS, challengeMaxWins);
		homeResources.resources.put(HomeResources.RESOURCE_FAVOURITE_CARD, favouriteCard == null ? 0 : favouriteCard.getIndex());

		homeResources.encode(stream);

		stream.putRrsInt32(0);

		int[] statItems = new int[] {
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
		stream.putRrsInt32(0); // 0

		stream.putRrsInt32(gems);
		stream.putRrsInt32(freeGems);

		stream.putRrsInt32(levelExperience);
		stream.putRrsInt32(level.getIndex());

		stream.putRrsInt32(0);

		if (name == null && isMyProfile) {
			stream.putRrsInt32(0);
		} else if (clan == null) {
			stream.putRrsInt32(1);
		} else {
			stream.putRrsInt32(9);
			clan.encode(stream);
		}

		// Matches played (global/tournament/challenge)?
		stream.putRrsInt32(402); // global?
		stream.putRrsInt32(0); // tournament
		stream.putRrsInt32(0); // unk

		stream.putRrsInt32(wins);
		stream.putRrsInt32(looses);
	}
}
