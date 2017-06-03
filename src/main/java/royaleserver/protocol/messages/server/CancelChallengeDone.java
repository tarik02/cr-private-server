package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public class CancelChallengeDone extends Message {
	public static final short ID = Info.CANCEL_CHALLENGE_DONE;

	public CancelChallengeDone() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);
		stream.put(Hex.toByteArray("00"));
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}
}
