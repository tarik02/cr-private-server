package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.protocol.messages.component.AllianceHeaderEntry;

public class JoinableAllianceList extends Message {
	public static final short ID = Info.JOINABLE_ALLIANCE_LIST;

	public AllianceHeaderEntry[] alliances;

	public JoinableAllianceList() {
		super(ID);

		alliances = new AllianceHeaderEntry[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		alliances = new AllianceHeaderEntry[stream.getRrsInt32()];
		for (int i = 0; i < alliances.length; ++i) {
			alliances[i].encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		stream.putRrsInt32((int)alliances.length);
		for (int i = 0; i < alliances.length; ++i) {
			alliances[i] = new AllianceHeaderEntry();
			alliances[i].decode(stream);
		}
	}
}
