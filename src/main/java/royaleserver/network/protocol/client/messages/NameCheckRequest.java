package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class NameCheckRequest extends ClientMessage {
	public static final short ID = Messages.NAME_CHECK_REQUEST;

	public String name;

	public NameCheckRequest() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		name = stream.getString();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleNameCheckRequest(this);
	}
}
