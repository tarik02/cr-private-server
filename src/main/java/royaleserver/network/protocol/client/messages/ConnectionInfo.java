package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class ConnectionInfo extends ClientMessage {
	public static final short ID = Messages.CONNECTION_INFO;

	public int ping;
	public String connectionInterface;

	public ConnectionInfo() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new ConnectionInfo();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleConnectionInfo(this);
	}

	@Override
	public void decode(DataStream stream) {
		ping = stream.getRrsInt32();
		connectionInterface = stream.getString();
	}
}
