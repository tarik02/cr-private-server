package royaleserver.network.protocol.server.components;

import royaleserver.utils.DataStream;

public class GameCard {
	public int cardId;
	public int level;

	public void encode(DataStream stream) {
		stream.putRrsInt32(cardId);
		stream.putRrsInt32(level - 1);
	}
}
