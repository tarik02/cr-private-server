package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

import royaleserver.protocol.messages.component.AllianceHeaderEntry;

public final class ClanJoinableResponse extends ServerMessage {
	public static final short ID = Messages.CLAN_JOINABLE_RESPONSE;

	public AllianceHeaderEntry[] alliances;

	public ClanJoinableResponse() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		stream.putRrsInt32(alliances.length);

		for (int i = 0; i < alliances.length; ++i) {
			alliances[i].encode(stream);
		}
	}
}
