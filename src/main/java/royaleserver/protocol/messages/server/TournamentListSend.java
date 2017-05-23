package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public class TournamentListSend extends Message {
	public static final short ID = Info.TOURNAMENT_LIST_SEND;

	public int delay;

	public TournamentListSend() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.put(Hex.toByteArray("00"));
	}
}
