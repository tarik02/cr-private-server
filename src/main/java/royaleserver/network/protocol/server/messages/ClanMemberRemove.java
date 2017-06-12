package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.ClanMember;
import royaleserver.utils.DataStream;

public class ClanMemberRemove extends ServerMessage {
	public static final short ID = Messages.CLAN_MEMBER_REMOVE;

	public ClanMember member;

	public ClanMemberRemove() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new ClanMemberRemove();
	}

	@Override
	public void encode(DataStream stream) {
		member.encode(stream);
	}
}
