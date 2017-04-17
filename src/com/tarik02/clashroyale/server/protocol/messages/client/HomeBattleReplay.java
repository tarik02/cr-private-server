package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class HomeBattleReplay extends Message {
	public static final short ID = Info.HOME_BATTLE_REPLAY;

	public long battleId;
	public byte serverId;
	public byte feedPosition;
	public byte gameMode;
	public long arena;

	public HomeBattleReplay() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(battleId);
		stream.putByte(serverId);
		stream.putByte(feedPosition);
		stream.putByte(gameMode);
		stream.putSCID(arena);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		battleId = stream.getBLong();
		serverId = stream.getByte();
		feedPosition = stream.getByte();
		gameMode = stream.getByte();
		arena = stream.getSCID();
	}
}
