package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class GameDeck extends Component {
	public GameCard cards[];

	public GameDeck() {
		cards = new GameCard[8];
		for (int i = 0; i < 8; ++i) {
			cards[i] = new GameCard();
			cards[i].cardId = 1 + i;
			cards[i].level = 1;
		}
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		if (cards.length != 8) {
			throw new RuntimeException("cards.length must be 4");
		}

		for (GameCard card : cards) {
			card.encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}
}
