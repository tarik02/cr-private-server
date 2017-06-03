package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

public class HomeBattleReplayData extends Message {
	public static final short ID = Info.HOME_BATTLE_REPLAY_DATA;

	public int unknown_0;
	public String replay;

	public HomeBattleReplayData() {
		super(ID);

		unknown_0 = 0;
		replay = "";
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(unknown_0);
		stream.putZipString(replay);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		unknown_0 = stream.getRrsInt32();
		replay = stream.getZipString();
	}
}
