package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class CancelMatchmake extends Message {
	public static final short ID = Info.CANCEL_MATCHMAKE;

	public int ping;
	public String connectionInterface;

	public CancelMatchmake() {
		super(ID);
	}
	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}

	public boolean handle(MessageHandler handler) throws Throwable {
		return handler.handleCancelMatchmake(this);
	}
}
