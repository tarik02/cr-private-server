package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceStreamEntryRemoved extends Message {
	public static final short ID = Info.ALLIANCE_STREAM_ENTRY_REMOVED;

	public long entryId;

	public AllianceStreamEntryRemoved() {
		super(ID);

		entryId = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(entryId);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		entryId = stream.getBLong();
	}
}
