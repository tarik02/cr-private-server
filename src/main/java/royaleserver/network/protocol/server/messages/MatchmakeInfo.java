package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public final class MatchmakeInfo extends ServerMessage {
	public static final short ID = Messages.MATCHMAKE_INFO;

	public MatchmakeInfo() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new MatchmakeInfo();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putBInt(0);
	}
}
