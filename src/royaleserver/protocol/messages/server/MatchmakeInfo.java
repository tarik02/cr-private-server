package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public class MatchmakeInfo extends Message {
	public static final short ID = Info.MATCHMAKE_INFO;

	public MatchmakeInfo() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);
		stream.put(Hex.toByteArray("00 00 00 00"));
	}
}
