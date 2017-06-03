package royaleserver.network.protocol.client;

import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public abstract class ClientMessage extends Message {
	protected ClientMessage(short id) {
		super(id);
	}

	public abstract void decode(DataStream stream);
	public abstract boolean handle(ClientMessageHandler handler) throws Throwable;
}
