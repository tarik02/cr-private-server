package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.ClanMember;
import royaleserver.utils.DataStream;

public class ClanMemberAdd extends ServerMessage {
	public static final short ID = Messages.CLAN_MEMBER_ADD;

	public ClanMember member;

	public ClanMemberAdd() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new ClanMemberAdd();
	}

	@Override
	public void encode(DataStream stream) {
		member.encode(stream);
	}
}
