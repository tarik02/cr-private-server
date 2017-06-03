package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public class Disconnected extends Message {
	public static final short ID = Info.DISCONNECTED;

	public int unknown_0;

	public Disconnected() {
		super(ID);

		unknown_0 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(unknown_0);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		unknown_0 = stream.getRrsInt32();
	}
}
