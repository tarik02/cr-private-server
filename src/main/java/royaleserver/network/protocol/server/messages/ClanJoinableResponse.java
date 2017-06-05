package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.ClanHeader;
import royaleserver.utils.DataStream;

public final class ClanJoinableResponse extends ServerMessage {
	public static final short ID = Messages.CLAN_JOINABLE_RESPONSE;

	public ClanHeader[] clans;

	public ClanJoinableResponse() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new ClanJoinableResponse();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(clans.length);

		for (int i = 0; i < clans.length; ++i) {
			clans[i].encode(stream);
		}
	}
}
