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

		// Сохрани себе где-нибудь, а потом удали. Я это для тебя писал прост))

		// Когда first == true:
		/*
			1 слот = offset(4) - first(true) - firstOffset(1) - slot(0) == 2 ^ 0
			2 слот = offset(4) - first(true) - firstOffset(2) - slot(1) == 2 ^ 1
			3 слот = offset(4) - first(true) - firstOffset(4) - slot(2) == 2 ^ 2
			4 слот = offset(4) - first(true) - firstOffset(8) - slot(3) == 2 ^ 3

			из этого мы видим, что firstOffset можно получить, с помощью метода 'возведение в степень'
		*/

		// ПРИМЕР: (4 слот = offset(4) - firstOffset(8) - slot(3) == 2 ^ 3)
		/*
			homeChests[0] = new HomeChest();
			homeChests[0].offset = 4;
			homeChests[0].first = true;
			homeChests[0].firstOffset = 8;
			homeChests[0].chest = Chest.by(10);
			homeChests[0].slot = 3;
			homeChests[0].status = 0;
		*/

		// Когда first != true:
		/*
			В данном случае, нам нужно работать с offset, а не с firstOffset, как это было выше.

			1 слот - не может быть, нужно строго соблюдать порядок и, если сундук с 'first = false' ->
			стоит перед сундуком с 'first', то будет что-то плохое.

			Offset зависит от firstOffset.
			После сундука первого сундука = offset(4) // Если, нам нужно поставить сундук после первого, то смело делаем offset = 4
			+ 1 после сундука первого сундука (если нужен пустой слот) = offset(8) // Если нам нужно пропустить 1 сундук после первого
			+ 2 сундука (если нужно 2 пустых слота) = offset(16)

			2 слот = offset(x) - first(false) - firstOffset(NULL) - slot(1)
			3 слот = offset(x) - first(false) - firstOffset(NULL) - slot(2)
			4 слот = offset(x) - first(false) - firstOffset(NULL) - slot(3)

			! А вообще, если будут вопросы с сундуками, то лучше спрашивай у меня c:
		*/

		// ПРИМЕР: (1, 4 слот)
		/*
			homeChests[0] = new HomeChest();
			homeChests[0].offset = 4;
			homeChests[0].first = true;
			homeChests[0].firstOffset = 1;
			homeChests[0].chest = Chest.by(10);
			homeChests[0].slot = 0;
			homeChests[0].status = 0;

			homeChests[1] = new HomeChest();
			homeChests[1].offset = 16;
			homeChests[1].chest = Chest.by(10);
			homeChests[1].slot = 3;
			homeChests[1].status = 0;
		*/


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
		stream.put(Hex.toByteArray("030000000000000002"));

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

		stream.put(Hex.toByteArray("0b08935f8506000381030a00011c08010900a3030000fa078c0101b9a1bd1700001a003508b5fdb2178c22000a000d0491aa9f1700000900330abaaea017b5090008001308a3b29f17050005002e027f0400120082010800a81f000c002a0a8e95d3171300180003aeeae51890fcd91ab0eae51803aeeae51890fcd91ab0eae51800018fd2f83e078cd2f83e8dd2f83e8ed2f83e8bd2f83e8fd2f83e9cd2f83eb1d2f83e0291d2f83ea0d2f83e019081a1fe0b00bb0201018ae6bf33020087822aa60859310f83593504039bb5ae0501000413b303059b5a0b7f000000"));

		stream.putRrsInt32((int)arena.getDbId());

		stream.put(Hex.toByteArray("021f993fbc3e000020b340b33e000000000001"));
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
