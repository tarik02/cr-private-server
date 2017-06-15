package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.utils.DataStream;

public class TournamentList extends ServerMessage {
	public static final short ID = Messages.TOURNAMENT_LIST_SEND;

	public byte[] sessionKey;

	public TournamentList() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new TournamentList();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putByte((byte)0x00);
	}
}
