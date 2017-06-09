package royaleserver.network.protocol.server.messages;

import royaleserver.logic.Chest;
import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.Card;
import royaleserver.network.protocol.server.components.Deck;
import royaleserver.network.protocol.server.components.HomeChest;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public final class HomeDataOwn extends HomeData {
	public static final short ID = Messages.HOME_DATA_OWN;

	public int accountCreatedTime;
	public int loginTime;

	public HomeChest homeChests[];
	public Card[] cards;

	public Deck currentDeck;
	public Deck[] decks;

	public String[] offers; // JSON
	public String[] challenges; // JSON

	public Card[] shopCards;
	public boolean giveSeasonReward;

	public HomeDataOwn() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new HomeDataOwn();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putBLong(homeId);

		stream.putRrsInt32(4194); // age / time?
		stream.putRrsInt32(1529); // age / time?

		stream.putRrsInt32(945100); // donation Cooldown Seconds NextFreeChest ?
		stream.putRrsInt32(945100); // donationCapacity

		stream.putRrsInt32(loginTime);

		stream.putByte((byte)0);

		stream.putRrsInt32(decks.length);
		for (Deck deck : decks) {
			stream.putRrsInt32(deck.cards.length);
			for (Card card : deck.cards) {
				stream.putRrsInt32((int)card.card.getScid().getValue());
			}
		}

		stream.put(Hex.toByteArray("ff"));
		currentDeck.encode(stream);

		stream.putRrsInt32(cards.length);
		for (Card card : cards) {
			card.encode(stream);
		}

		stream.putRrsInt32(0);

		stream.put(Hex.toByteArray("ff"));
		stream.putRrsInt32(60);
		stream.putRrsInt32(6);

		// Unknown structure, possible for challenges/offers
		for (int i = 0; i < 8; i++) {
			stream.putByte((byte)127);

			stream.putRrsInt32(0);
			stream.putRrsInt32(0);
			stream.putRrsInt32(0);
			stream.putRrsInt32(0);

			stream.putRrsInt32(1);

			// already showed? (offers/challenge id)
			stream.putRrsInt32(i);
		}

		stream.putRrsInt32((int)System.currentTimeMillis());
		stream.putRrsInt32(1);
		stream.putRrsInt32(0);

		// offers struct
		int countOfOffers = offers.length;
		int countOfChallenges = challenges.length;

		stream.putRrsInt32(countOfOffers + countOfChallenges);

		for (int i = 0; i < countOfOffers; i++) {
			stream.putByte((byte)i);
			stream.putString("TestOffer");

			// type
			// 1 - to buy
			// 2 - challenge
			stream.putRrsInt32(1);

			// time
			stream.putRrsInt32((int)System.currentTimeMillis()); // start
			stream.putRrsInt32(1494930622); // end
			stream.putRrsInt32((int)System.currentTimeMillis()); // start?

			stream.put(Hex.toByteArray("00 00 00 00 00 00 00 00"));

			stream.putString("TestOffer");
			stream.putString(offers[i]); // json
		}

		// challenges struct
		for (int i = 0; i < countOfChallenges; i++) {
			stream.putByte((byte)(countOfOffers + i));
			stream.putString("Test Challenge");

			// type
			// 1 - to buy
			// 2 - challenge
			stream.putRrsInt32(2);

			// time
			stream.putRrsInt32((int)System.currentTimeMillis()); // start
			stream.putRrsInt32(1494937500); // end
			stream.putRrsInt32((int)System.currentTimeMillis()); // start?

			stream.put(Hex.toByteArray("00 00 00 00 00 00 00 00"));

			stream.putString("Test Challenge");
			stream.putString(challenges[i]); // json
		}

		stream.put(Hex.toByteArray("0000000000007f000000021d011e05")); // short version

		// year[4]-month[2]-day[2]
		stream.putString("{\"ID\":\"CARD_RELEASE\",\"Params\":{\"Assassin\":\"20170324\",\"Heal\":\"20170501\"}}");

		if (homeChests.length != 0) {
			homeChests[0].first = true;
			for (HomeChest chest : homeChests) {
				chest.encode(stream);
			}
		}

		stream.put(Hex.toByteArray("0000"));

		// free treasure chest next comedown
		stream.putRrsInt32(72000); // 72000 = 1 hour => 1200 = 1 min => 20 = 1 sec

		stream.putRrsInt32(287360); // seconds?
		stream.putRrsInt32(1496659547); // timestamp

		// struct of CrownChest
		stream.put(Hex.toByteArray("00007f"));
		stream.put(Hex.toByteArray("00000000000000000000"));
		stream.putRrsInt32(0); // count of crowns | 10 == 1 chest

		// unknown struct
		stream.put(Hex.toByteArray("00"));
		stream.put(Hex.toByteArray("00007f"));

		stream.putRrsInt32(1455020); // seconds?
		stream.putRrsInt32(1683240); // seconds?

		stream.putRrsInt32(1496732218); // timestamp

		// unknown struct
		stream.put(Hex.toByteArray("00"));
		stream.put(Hex.toByteArray("00007f"));
		stream.putRrsInt32(13); // ? 3 - name not set
		stream.put(Hex.toByteArray("0000000000000002"));

		// last information
		stream.putRrsInt32(lastLevel.getIndex());
		stream.putByte((byte)36);
		stream.putByte((byte)lastArena.getIndex());

		stream.put(Hex.toByteArray("c5d9c1ba09"));

		stream.putRrsInt32(2);
		stream.putRrsInt32(2);

		// next shop update
		stream.putRrsInt32(72000); // 72000 = 1 hour => 1200 = 1 min => 20 = 1 sec
		stream.putRrsInt32(72000);

		// next update (timestamp)
		stream.putRrsInt32((int)System.currentTimeMillis());

		stream.putRrsInt32(shopCards.length);

		for (int i = 0; i < shopCards.length; ++i) {
			Card card = shopCards[i];

			stream.putByte((byte)0x01);
			card.encode(stream);
			stream.putByte((byte)0x00);
			stream.putRrsInt32(card.card.getType());
			stream.putRrsInt32(card.card.getScid().getLow());
			stream.putRrsInt32(i);
		}

		stream.put(Hex.toByteArray("00"));

		stream.put(Hex.toByteArray("00007f"));
		stream.put(Hex.toByteArray("00007f"));
		stream.put(Hex.toByteArray("00007f"));

		// unknown ints
		stream.putRrsInt32(20);
		stream.putRrsInt32(17);
		stream.putRrsInt32(2035);
		stream.putRrsInt32(1744);
		stream.putRrsInt32(0);

		// unk struct
		stream.writeStructureRRS(new int[]{193, 8, 0});

		stream.putRrsInt32(1);
		stream.putRrsInt32(26);
		stream.putRrsInt32(39);
		stream.putRrsInt32(1);
		stream.putRrsInt32(9);
		stream.putRrsInt32(0);
		stream.putRrsInt32(782);

		stream.put(Hex.toByteArray("0000"));
		stream.put(Hex.toByteArray("fa"));

		// wtf??!1
		stream.putRrsInt32(7);
		stream.putRrsInt32(22);
		stream.putRrsInt32(7);
		stream.putRrsInt32(24517854);
		stream.putRrsInt32(31);
		stream.putRrsInt32(1);
		stream.putRrsInt32(8);

		stream.putRrsInt32(0);
		stream.putRrsInt32(61);
		stream.putRrsInt32(6);
		stream.putRrsInt32(24531145);
		stream.putRrsInt32(215);
		stream.putRrsInt32(0);
		stream.putRrsInt32(5);

		stream.putRrsInt32(0);
		stream.putRrsInt32(42);
		stream.putRrsInt32(8);
		stream.putRrsInt32(24801577);
		stream.putRrsInt32(775);
		stream.putRrsInt32(0);
		stream.putRrsInt32(7);

		stream.putRrsInt32(0);
		stream.putRrsInt32(65);
		stream.putRrsInt32(7);
		stream.putRrsInt32(0);
		stream.putRrsInt32(5);
		stream.putRrsInt32(0);
		stream.putRrsInt32(8);

		stream.putRrsInt32(0);
		stream.putRrsInt32(1);
		stream.putRrsInt32(9);
		stream.putRrsInt32(0);
		stream.putRrsInt32(351);
		stream.putRrsInt32(0);
		stream.putRrsInt32(6);

		stream.putRrsInt32(0);
		stream.putRrsInt32(12);
		stream.putRrsInt32(6);
		stream.putRrsInt32(24476166);
		stream.putRrsInt32(16);
		stream.putRrsInt32(0);
		stream.putRrsInt32(5);

		stream.putRrsInt32(0);
		stream.putRrsInt32(15);
		stream.putRrsInt32(6);
		stream.putRrsInt32(24471993);
		stream.putRrsInt32(30);
		stream.putRrsInt32(0);
		stream.putRrsInt32(3);

		stream.putRrsInt32(0);
		stream.putRrsInt32(40);
		stream.putRrsInt32(6);
		stream.putRrsInt32(24577728);
		stream.putRrsInt32(182);
		stream.putRrsInt32(0);
		stream.putRrsInt32(4);

		stream.put(Hex.toByteArray("00"));

		stream.writeStructureRRS(new int[]{26000046, 26000016});
		stream.writeStructureRRS(new int[]{26000046, 26000016});

		stream.put(Hex.toByteArray("00"));

		stream.writeStructureRRS(new int[]{66000014, 66000015});
		stream.writeStructureRRS(new int[]{66000013, 66000012, 66000014, 66000028});
		stream.writeStructureRRS(new int[]{66000029});
		stream.writeStructureRRS(new int[]{1608786000}); // player id?? in rrs int??

		stream.put(Hex.toByteArray("00"));

		stream.putRrsInt32(121); // changed
		stream.putRrsInt32(1);
		stream.putRrsInt32(1);
		stream.putRrsInt32(54000010);

		stream.putRrsInt32(giveSeasonReward ? 2 : 1); // 1 - without reward, 2 - with reward
		stream.putRrsInt32(19); // changed
		stream.putRrsInt32(223); // changed

		stream.putRrsInt32(1); // 1

		stream.put(Hex.toByteArray(giveSeasonReward ? "59" : "a9")); // a9 - no seasonReward ||  - with seasonReward
		stream.put(Hex.toByteArray(giveSeasonReward ? "39" : "41"));

		stream.putRrsInt32(14); // 15

		if (giveSeasonReward) {
			stream.putRrsInt32(5699);
			stream.putRrsInt32(53);
			stream.putRrsInt32(4);
			stream.putRrsInt32(3);
			stream.putRrsInt32(5623131);
			stream.putRrsInt32(1);
			stream.putRrsInt32(0);
			stream.putRrsInt32(4);
			stream.putRrsInt32(19);
			stream.putRrsInt32(243);
			stream.putRrsInt32(5);
			stream.putRrsInt32(5787);
			stream.putRrsInt32(11);
		}

		stream.put(Hex.toByteArray("7f000000"));
		stream.putRrsInt32(arena.getIndex() + 1);

		// items count
		stream.putRrsInt32(2);

		// first item
		stream.putRrsInt32(31); // type
		stream.putRrsInt32(4057); // now/max in now season?
		stream.putRrsInt32(4028); // best season trophies

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		// second item
		stream.putRrsInt32(32); // type
		stream.putRrsInt32(4147); // highest trophies
		stream.putRrsInt32(4019); // previous season trophies

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		stream.put(Hex.toByteArray("00000001"));

		super.encode(stream);

		// WinStreak
		stream.putByte((byte)127); // 127 = -64 (varint) = null

		stream.putRrsInt32(8);

		// Tournament?
		stream.putRrsInt32(0);

		stream.put(Hex.toByteArray("0000"));

		// timestamp countdown?
		stream.putRrsInt32(119049974);

		// account create date/time
		stream.putRrsInt32(accountCreatedTime);

		// time played?
		stream.putRrsInt32(1881931);
	}
}
