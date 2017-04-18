package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class BattleReportStream extends Message {
	public static final short ID = Info.BATTLE_REPORT_STREAM;

	public long accountID;

	public BattleReportStream() {
		super(ID);

		accountID = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(accountID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		accountID = stream.getBLong();
	}
}
