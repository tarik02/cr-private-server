package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class CancelChallengeDone extends Message {
	public static final short ID = Info.CANCEL_CHALLENGE_DONE;

	public CancelChallengeDone() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}
}
