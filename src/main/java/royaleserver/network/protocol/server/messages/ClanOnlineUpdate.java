package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.utils.DataStream;

public class ClanOnlineUpdate extends ServerMessage {
	public static final short ID = Messages.CLAN_ONLINE_UPDATE;

	public int membersOnline;
	public int unknown_1;

	public ClanOnlineUpdate() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new ClanOnlineUpdate();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(membersOnline);
		stream.putRrsInt32(unknown_1);
	}
}
