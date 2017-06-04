package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class NameCheck extends ClientMessage {
	public static final short ID = Messages.NAME_CHECK;

	public String name;

	public NameCheck() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		name = stream.getString();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleNameCheck(this);
	}
}
