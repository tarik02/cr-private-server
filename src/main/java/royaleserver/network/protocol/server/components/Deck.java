package royaleserver.network.protocol.server.components;

import royaleserver.utils.DataStream;

public class Deck {
	public Card cards[];

	public void encode(DataStream stream) {
		if (cards.length != 8) {
			throw new RuntimeException("cards.length must be 8");
		}

		for (Card card : cards) {
			card.encode(stream);
		}
	}
}
