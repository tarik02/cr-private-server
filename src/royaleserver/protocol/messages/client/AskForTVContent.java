package royaleserver.protocol.messages.client;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

import royaleserver.utils.SCID;

public class AskForTVContent extends Message {
	public static final short ID = Info.ASK_FOR_TV_CONTENT;

	public SCID arena;

	public AskForTVContent() {
		super(ID);

		arena = new SCID();
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putSCID(arena);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		arena = stream.getSCID();
	}

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleAskForTVContent(this);
	}
}
