package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class Deck extends Component {
	public Card cards[];

	public Deck() {
		cards = new Card[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		if (cards.length != 8) {
			throw new RuntimeException("cards.length must be 8");
		}

		for (Card card : cards) {
			card.encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}
}