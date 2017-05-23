package royaleserver.protocol.messages.component;

import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public class GameCard extends Component {

	public int cardId;
	public int level;

	public GameCard() {
		cardId = 1;
		level = 1;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(cardId);
		stream.putRrsInt32(level - 1);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}
}
