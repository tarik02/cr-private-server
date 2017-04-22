package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.protocol.messages.component.AllianceRankingEntry;

public class AllianceLocalRankingList extends Message {
	public static final short ID = Info.ALLIANCE_LOCAL_RANKING_LIST;

	public AllianceRankingEntry[] entries;

	public AllianceLocalRankingList() {
		super(ID);

		entries = new AllianceRankingEntry[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		entries = new AllianceRankingEntry[stream.getBInt()];
		for (int i = 0; i < entries.length; ++i) {
			entries[i].encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		stream.putBInt((int)entries.length);
		for (int i = 0; i < entries.length; ++i) {
			entries[i] = new AllianceRankingEntry();
			entries[i].decode(stream);
		}
	}
}
