package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public class CancelMatchmakeDone extends Message {
	public static final short ID = Info.CANCEL_MATCHMAKE_DONE;

	public CancelMatchmakeDone() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.put(Hex.toByteArray("00"));
	}
}
