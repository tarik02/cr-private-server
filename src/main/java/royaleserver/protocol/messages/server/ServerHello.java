package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public class ServerHello extends Message {
	public static final short ID = Info.SERVER_HELLO;

	public byte[] sessionKey;

	public ServerHello() {
		super(ID);

		sessionKey = new byte[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByteSet(sessionKey);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		sessionKey = stream.getByteSet();
	}
}
