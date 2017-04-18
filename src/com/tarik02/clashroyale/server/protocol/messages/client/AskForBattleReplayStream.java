package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AskForBattleReplayStream extends Message {
	public static final short ID = Info.ASK_FOR_BATTLE_REPLAY_STREAM;

	public long accountID;

	public AskForBattleReplayStream() {
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
