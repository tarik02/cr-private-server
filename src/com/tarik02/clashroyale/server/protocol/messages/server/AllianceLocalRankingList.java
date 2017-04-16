package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.protocol.messages.MessageFactory;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceLocalRankingList extends Message {
	public static final short ID = Info.ALLIANCE_LOCAL_RANKING_LIST;

	public AllianceLocalRankingList() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}
}
