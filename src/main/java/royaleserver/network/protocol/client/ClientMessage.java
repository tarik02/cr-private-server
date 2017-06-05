package royaleserver.network.protocol.client;

import royaleserver.network.protocol.*;

import royaleserver.utils.DataStream;

public abstract class ClientMessage extends Message implements FactoryTarget<ClientMessage> {
	protected ClientMessage(short id) {
		super(id);
	}

	public abstract void decode(DataStream stream);
	public abstract boolean handle(ClientMessageHandler handler) throws Throwable;
}
