package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class Disconnected extends ServerMessage {
	public static final short ID = Messages.DISCONNECTED;

	public int unknown_0;

	public Disconnected() {
		super(ID);

		unknown_0 = 0;
	}

	@Override
	public ServerMessage create() {
		return new Disconnected();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(unknown_0);
	}
}
