package royaleserver.network.protocol.server.messages;

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
	public int currentDeckSlot;

	public Deck currentDeck;
	public Card[] cardsAfterDeck;
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

		stream.putRrsInt32(4667); // age / time?
		stream.putRrsInt32(1735); // age / time?

		stream.putRrsInt32(1351400); // donation Cooldown Seconds NextFreeChest ?
		stream.putRrsInt32(1563600); // donationCapacity

		stream.putByte((byte)-67);
		stream.putRrsInt32(-11700695);

		stream.putByte((byte)0);

		stream.putRrsInt32(decks.length);
		for (Deck deck : decks) {
			stream.putRrsInt32(deck.cards.length);
			for (Card card : deck.cards) {
				if (card != null) {
					stream.putRrsInt32((int)card.card.getScid().getValue());
				} else {
					stream.putRrsInt32(0);
				}
			}
		}

		stream.put(Hex.toByteArray("ff"));
		currentDeck.encode(stream);

		stream.putRrsInt32(cardsAfterDeck.length);
		for (Card card : cardsAfterDeck) {
			card.encode(stream);
		}
		stream.putByte((byte) currentDeckSlot);
		stream.put(Hex.toByteArray("ff13067f000000008a01007f000000002b007f0000000002007f000000000d007f000000008d01007f000000008f01007f000000009201007f000000003339bcb69f940b0100"));

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

		stream.put(Hex.toByteArray("000000")); // short version

		stream.putRrsInt32(1);

		stream.putRrsInt32(73000028);
		stream.putRrsInt32(2);
		stream.putRrsInt32(1);

		stream.putRrsInt32(18);
		stream.putRrsInt32(0);
		stream.putRrsInt32(1497266100);
		stream.put(Hex.toByteArray("000000"));

		stream.putRrsInt32(4);

		stream.putRrsInt32(61);
		stream.putRrsInt32(1);

		stream.putRrsInt32(62);
		stream.putRrsInt32(1);

		stream.putRrsInt32(64);
		stream.putRrsInt32(1);

		stream.putRrsInt32(65);
		stream.putRrsInt32(1);

		stream.putRrsInt32(4);

		stream.putRrsInt32(61);
		stream.putRrsInt32(2);

		stream.putRrsInt32(62);
		stream.putRrsInt32(2);

		stream.putRrsInt32(64);
		stream.putRrsInt32(3);

		stream.putRrsInt32(65);
		stream.putRrsInt32(2);

		// JSONs count
		stream.putRrsInt32(2);

		// first
		stream.putString("{\"ID\":\"CARD_RELEASE\",\"Params\":{}}");
		stream.putByte((byte)4);

		// second
		stream.putString("{\"ID\":\"CLAN_CHEST\",\"Params\":{\"StartTime\":\"20170317T070000.000Z\",\"ActiveDuration\":\"P3dT0h\",\"InactiveDuration\":\"P4dT0h\",\"ChestType\":[\"ClanCrowns\"]}}");
		stream.putByte((byte)4);

		if (homeChests.length != 0) {
			for (HomeChest chest : homeChests) {
				chest.encode(stream);
			}
		}

		stream.put(Hex.toByteArray("00"));
		stream.putRrsInt32(35260);
		stream.putRrsInt32(247460);
		stream.putRrsInt32(1497625774);

		// free treasure chest next comedown (00007f - already downed)
		stream.put(Hex.toByteArray("00007f"));

		stream.putRrsInt32(1);
		stream.putRrsInt32(19);
		stream.putRrsInt32(42);
		stream.putRrsInt32(1);
		stream.putRrsInt32(4666);

		// struct of CrownChest
		stream.put(Hex.toByteArray("00"));
		stream.put(Hex.toByteArray("7f0000"));
		stream.put(Hex.toByteArray("00000000000000000000"));
		stream.putRrsInt32(0);

		// unknown struct
		stream.put(Hex.toByteArray("00"));
		stream.put(Hex.toByteArray("00007f"));
		stream.putRrsInt32(242660); // seconds?
		stream.putRrsInt32(1726780); // seconds?
		stream.putRrsInt32(1497381256); // timestamp

		// unknown struct
		stream.put(Hex.toByteArray("00"));
		stream.put(Hex.toByteArray("00007f"));
		stream.putRrsInt32(name == null ? 13 : 3); // ? 3 - name not set
		stream.put(Hex.toByteArray("0000000000000002"));

		// last information
		stream.putRrsInt32(lastLevel.getIndex());
		stream.putByte((byte)54);
		stream.putByte((byte)lastArena.getIndex());

		stream.put(Hex.toByteArray("c1acbbc4060606"));

		// next shop update
		stream.putRrsInt32(72000); // 72000 = 1 hour => 1200 = 1 min => 20 = 1 sec
		stream.putRrsInt32(72000);

		// next update (timestamp)
		stream.putRrsInt32(1497311999);

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
		stream.putRrsInt32(7);
		stream.putRrsInt32(3);
		stream.putRrsInt32(2236);
		stream.putRrsInt32(1896);
		stream.putRrsInt32(657);
		stream.putRrsInt32(3);
		stream.putRrsInt32(193);
		stream.putRrsInt32(10);
		stream.putRrsInt32(0);

		stream.putRrsInt32(1);
		stream.putRrsInt32(26);
		stream.putRrsInt32(11);
		stream.putRrsInt32(1);
		stream.putRrsInt32(10);
		stream.putRrsInt32(0);
		stream.putRrsInt32(850);

		stream.put(Hex.toByteArray("0000"));
		stream.put(Hex.toByteArray("fa"));

		// wtf??!1
		stream.putRrsInt32(7);
		stream.putRrsInt32(16);
		stream.putRrsInt32(3);
		stream.putRrsInt32(24500694);
		stream.putRrsInt32(1);
		stream.putRrsInt32(0);
		stream.putRrsInt32(9);

		stream.putRrsInt32(0);
		stream.putRrsInt32(5);
		stream.putRrsInt32(3);
		stream.putRrsInt32(24538909);
		stream.putRrsInt32(1);
		stream.putRrsInt32(0);
		stream.putRrsInt32(5);

		stream.putRrsInt32(0);
		stream.putRrsInt32(46);
		stream.putRrsInt32(3);
		stream.putRrsInt32(-64);
		stream.putRrsInt32(4);
		stream.putRrsInt32(0);
		stream.putRrsInt32(6);

		stream.putRrsInt32(0);
		stream.putRrsInt32(85);
		stream.putRrsInt32(3);
		stream.putRrsInt32(-64);
		stream.putRrsInt32(3);
		stream.putRrsInt32(0);
		stream.putRrsInt32(13);

		stream.putRrsInt32(0);
		stream.putRrsInt32(2);
		stream.putRrsInt32(8);
		stream.putRrsInt32(0);
		stream.putRrsInt32(1350);
		stream.putRrsInt32(112);
		stream.putRrsInt32(7);

		stream.putRrsInt32(0);
		stream.putRrsInt32(37);
		stream.putRrsInt32(6);
		stream.putRrsInt32(24769249);
		stream.putRrsInt32(1);
		stream.putRrsInt32(1);
		stream.putRrsInt32(4);

		stream.putRrsInt32(0);
		stream.putRrsInt32(80);
		stream.putRrsInt32(3);
		stream.putRrsInt32(24474878);
		stream.putRrsInt32(2);
		stream.putRrsInt32(0);
		stream.putRrsInt32(9);

		stream.putRrsInt32(0);
		stream.putRrsInt32(81);
		stream.putRrsInt32(9);
		stream.putRrsInt32(24528284);
		stream.putRrsInt32(84);
		stream.putRrsInt32(83);
		stream.putRrsInt32(15);

		stream.put(Hex.toByteArray("0000"));

		// new unknown ints
		stream.putRrsInt32(3);
		stream.putRrsInt32(26);
		stream.putRrsInt32(46);
		stream.putRrsInt32(28);
		stream.putRrsInt32(16);
		stream.putRrsInt32(26);
		stream.putRrsInt32(48);
		stream.putRrsInt32(0);
		stream.putRrsInt32(1);
		stream.putRrsInt32(3);
		stream.putRrsInt32(-537760);
		stream.putRrsInt32(6);
		stream.putRrsInt32(1);
		stream.putRrsInt32(0);

		stream.writeStructureRRS(new int[]{66000014, 66000015});
		stream.writeStructureRRS(new int[]{66000013, 66000012, 66000014, 66000028});
		stream.putRrsInt32(1);
		stream.writeStructureRRS(new int[]{66000029});
		stream.writeStructureRRS(new int[]{1608786000}); // player id?? in rrs int??

		stream.put(Hex.toByteArray("00"));

		stream.putRrsInt32(130); // changed
		stream.putRrsInt32(1);

		stream.writeStructureRRS(new int[]{54000010});

		stream.putRrsInt32(2); // 1 - without reward, 2 - with reward
		stream.putRrsInt32(1); // changed
		stream.putRrsInt32(232041); // changed

		stream.putRrsInt32(75); // 1

		stream.putBInt(1497596551);
		stream.putBInt(1497855751);

		stream.putRrsInt32(4778892);
		stream.put(Hex.toByteArray("00 00 00 00 00 00 00 00 01 00"));

		stream.putBInt(37635);

		super.encode(stream);

		stream.putByte((byte)127);
		stream.putRrsInt32(23);
		stream.putRrsInt32(2);

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		stream.putRrsInt32(119049974);
		stream.putRrsInt32(accountCreatedTime);
		stream.putRrsInt32(1881931);
	}
}
