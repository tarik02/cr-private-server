package royaleserver.protocol.messages.client;

import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

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

		System.out.println("Buffer: " + Hex.toHexString(stream.get(1000)));
	}

	public boolean handle(MessageHandler handler) throws Throwable {
		return handler.handleLeaveAlliance(this);
	}
}
