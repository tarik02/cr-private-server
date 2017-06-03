package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public class InboxGlobal extends Message {
	public static final short ID = Info.INBOX_GLOBAL;

	public byte unknown_0;

	public InboxGlobal() {
		super(ID);

		unknown_0 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(unknown_0);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		unknown_0 = stream.getByte();
	}
}
