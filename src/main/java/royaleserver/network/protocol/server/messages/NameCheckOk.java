package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.utils.DataStream;

public final class NameCheckOk extends ServerMessage {
	public static final short ID = Messages.NAME_CHECK_OK;

	public String name;

	public NameCheckOk() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new NameCheckOk();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putString(name);
	}
}
