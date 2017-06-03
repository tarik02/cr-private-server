package royaleserver.protocol.messages.server;

import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
import royaleserver.utils.DataStream;

import royaleserver.protocol.messages.component.BattleStreamEntry;

public class BattleReportStream extends Message {
	public static final short ID = Info.BATTLE_REPORT_STREAM;

	public long accountID;
	public BattleStreamEntry[] BattleStreamEntries;

	public BattleReportStream() {
		super(ID);

		accountID = 0;
		BattleStreamEntries = new BattleStreamEntry[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(accountID);
		BattleStreamEntries = new BattleStreamEntry[stream.getRrsInt32()];
		for (int i = 0; i < BattleStreamEntries.length; ++i) {
			BattleStreamEntries[i].encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		accountID = stream.getBLong();
		stream.putRrsInt32(BattleStreamEntries.length);
		for (int i = 0; i < BattleStreamEntries.length; ++i) {
			BattleStreamEntries[i] = new BattleStreamEntry();
			BattleStreamEntries[i].decode(stream);
		}
	}
}
