package royaleserver.network.protocol.server;

import royaleserver.network.protocol.*;

import royaleserver.utils.DataStream;

public abstract class ServerMessage extends Message implements FactoryTarget<ServerMessage> {
	protected ServerMessage(short id) {
		super(id);
	}

	public abstract void encode(DataStream stream);
}
