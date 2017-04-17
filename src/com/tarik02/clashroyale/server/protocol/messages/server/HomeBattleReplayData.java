package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class HomeBattleReplayData extends Message {
	public static final short ID = Info.HOME_BATTLE_REPLAY_DATA;

	public String replay;

	public HomeBattleReplayData() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putZipString(replay);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		replay = stream.getZipString();
	}
}
