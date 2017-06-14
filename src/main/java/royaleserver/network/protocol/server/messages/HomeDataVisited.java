package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.Deck;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public final class HomeDataVisited extends HomeData {
	public static final short ID = Messages.HOME_DATA_VISITED;

	public int place;
	public Deck deck;

	public HomeDataVisited() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new HomeDataVisited();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(250);
		stream.putRrsInt32(place);

		stream.put(Hex.toByteArray("ff01"));
		deck.encode(stream);

		stream.putBLong(homeId);

		stream.putByte((byte)0);
		stream.putByte((byte)0);

		stream.putByte((byte)1);

		super.encode(stream);

		// Unknown
		stream.put(Hex.toByteArray("7e"));
		stream.putRrsInt32(23);
		stream.putRrsInt32(2);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(2);

		super.encode(stream);

		stream.put(Hex.toByteArray("7e"));
		stream.putRrsInt32(23);
		stream.putRrsInt32(2);

		stream.put(Hex.toByteArray("00 00 00 00"));

	}
}
