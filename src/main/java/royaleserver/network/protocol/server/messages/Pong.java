package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class Pong extends ServerMessage {
	public static final short ID = Messages.PONG;

	public Pong() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new Pong();
	}

	@Override
	public void encode(DataStream stream) {
	}
}
