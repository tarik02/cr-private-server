package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.protocol.messages.component.AllianceStreamComponent;

public class AllianceStream extends Message {
	public static final short ID = Info.ALLIANCE_STREAM;

	public AllianceStreamComponent[] entries;

	public AllianceStream() {
		super(ID);

		entries = new AllianceStreamComponent[0];
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		entries = new AllianceStreamComponent[stream.getRrsInt32()];
		for (int i = 0; i < entries.length; ++i) {
			entries[i].encode(stream);
		}
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		stream.putRrsInt32((int)entries.length);
		for (int i = 0; i < entries.length; ++i) {
			entries[i] = new AllianceStreamComponent();
			entries[i].decode(stream);
		}
	}
}
