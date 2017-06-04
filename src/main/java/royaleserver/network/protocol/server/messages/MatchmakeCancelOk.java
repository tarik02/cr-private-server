package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public final class MatchmakeCancelOk extends ServerMessage {
	public static final short ID = Messages.MATCHMAKE_CANCEL_OK;

	public MatchmakeCancelOk() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		stream.put(Hex.toByteArray("00"));
	}
}
