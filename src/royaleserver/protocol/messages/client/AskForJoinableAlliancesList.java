package royaleserver.protocol.messages.client;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;

public class AskForJoinableAlliancesList extends Message {
	public static final short ID = Info.ASK_FOR_JOINABLE_ALLIANCES_LIST;

	public AskForJoinableAlliancesList() {
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

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleAskForJoinableAlliancesList(this);
	}
}
