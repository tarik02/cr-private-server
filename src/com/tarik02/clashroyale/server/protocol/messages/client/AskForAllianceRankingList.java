package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AskForAllianceRankingList extends Message {
	public static final short ID = Info.ASK_FOR_ALLIANCE_RANKING_LIST;

	public byte region;

	public AskForAllianceRankingList() {
		super(ID);

		region = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(region);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		region = stream.getByte();
	}
}
