package royaleserver.network.protocol.server.components;

import royaleserver.utils.DataStream;

public class GameDeck {
	public GameCard cards[];

	public GameDeck() {
		cards = new GameCard[8];
		for (int i = 0; i < 8; ++i) {
			cards[i] = new GameCard();
			cards[i].cardId = 1 + i;
			cards[i].level = 1;
		}
	}

	public void encode(DataStream stream) {
		if (cards.length != 8) {
			throw new RuntimeException("cards.length must be 4");
		}

		for (GameCard card : cards) {
			card.encode(stream);
		}
	}
}
