package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.component.AllianceMemberEntry;
import royaleserver.utils.DataStream;

public class AllianceMember extends Message {
	public static final short ID = Info.ALLIANCE_MEMBER;

	public AllianceMemberEntry member;

	public AllianceMember() {
		super(ID);

		member = new AllianceMemberEntry();
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		member.encode(stream);
	}
}
