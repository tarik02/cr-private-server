package royaleserver.network.protocol.server;

import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public abstract class ServerMessage extends Message {
	protected ServerMessage(short id) {
		super(id);
	}

	public abstract void encode(DataStream stream);
}
