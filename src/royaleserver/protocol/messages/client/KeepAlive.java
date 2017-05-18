package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class KeepAlive extends Message {
	public static final short ID = Info.KEEP_ALIVE;

	public KeepAlive() {
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

	public boolean handle(MessageHandler handler) throws Throwable {
		return handler.handleKeepAlive(this);
	}
}
