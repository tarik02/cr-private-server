package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.HomeChest;
import royaleserver.protocol.messages.component.Card;
import royaleserver.protocol.messages.component.Deck;
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

		stream.put(Hex.toByteArray("00011b00012800119c91cf4502019c91cf4504019d91cf4501019d91cf4502019d91cf4503019d91cf4505019d91cf450601a091cf450101a091cf450201a091cf4503019f91cf4503019f91cf450601a391cf450101a391cf450201a391cf450301a491cf450401a891cf4502010e0080c4be8b0b010000042701280129012a05"));

		// year[4]-month[2]-day[2]
		stream.putString("{\"ID\":\"CARD_RELEASE\",\"Params\":{\"Assassin\":\"20170324\",\"Heal\":\"20170501\"}}");

		if (homeChests.length != 0) {
			homeChests[0].first = true;
			for (HomeChest chest : homeChests) {
				chest.encode(stream);
			}
		}

		// Not decoded yet
		stream.put(Hex.toByteArray("0000809423809423849fe6920b00007f0113070102007f0000000000000000000000000000a8ced101b0f6d201a6fbee920ba8ced101b0f6d201a6fbee920b0000007f0d0000000000000002"));

		stream.putRrsInt32(lastLevel.getIndex());
		stream.putByte((byte)36);
		stream.putByte((byte)lastArena.getArena());

		stream.put(Hex.toByteArray("c5d9c1ba09"));

		stream.putRrsInt32(2);
		stream.putRrsInt32(2);

		// time ? seconds
		stream.putRrsInt32(888140);
		stream.putRrsInt32(888140);

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

		stream.put(Hex.toByteArray("0000007f00007f00007f1411b31f901b000381030800011a270109008e0c0000fa0716079ef3b0171f0108003d0689c3b21797030005002a08a9c4d317870c00070081010700050008000109009f050006000c0686e8ab17100005000f06b9a6ab171e0003002806809bb817b60200040002aeeae51890fcd91a02aeeae51890fcd91a00028ed2f83e8fd2f83e048dd2f83e8cd2f83e8ed2f83e9cd2f83e019dd2f83e019081a1fe0b00b90101018ae6bf3301139f0301a9410e7fb012880300000000000000"));

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
