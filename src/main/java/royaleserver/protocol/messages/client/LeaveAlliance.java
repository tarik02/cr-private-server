package royaleserver.protocol.messages.client;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.MessageHandler;
import royaleserver.utils.DataStream;

public class LeaveAlliance extends Message {
	public static final short ID = Info.LEAVE_ALLIANCE;
	public long clanID = 0;

	public LeaveAlliance() {
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
		return handler.handleLeaveAlliance(this);
	}
}
