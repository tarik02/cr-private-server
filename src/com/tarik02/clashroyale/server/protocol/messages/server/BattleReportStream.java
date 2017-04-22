package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.protocol.messages.component.BattleStreamEntry;

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
		stream.putRrsInt32((int)BattleStreamEntries.length);
		for (int i = 0; i < BattleStreamEntries.length; ++i) {
			BattleStreamEntries[i] = new BattleStreamEntry();
			BattleStreamEntries[i].decode(stream);
		}
	}
}
