package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public class ServerHello extends ServerMessage {
	public static final short ID = Messages.SERVER_HELLO;

	public byte[] sessionKey;

	public ServerHello() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		stream.putByteSet(sessionKey);
	}
}
