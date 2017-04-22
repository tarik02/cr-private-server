package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.protocol.messages.component.AllianceStreamComponent;

public class AllianceStreamEntry extends Message {
	public static final short ID = Info.ALLIANCE_STREAM_ENTRY;

	public AllianceStreamComponent entry;

	public AllianceStreamEntry() {
		super(ID);

		entry = new AllianceStreamComponent();
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		entry.encode(stream);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		entry.decode(stream);
	}
}
