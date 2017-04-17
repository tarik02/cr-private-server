package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceData extends Message {
	public static final short ID = Info.ALLIANCE_DATA;

	public String description;

	public AllianceData() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(description);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		description = stream.getString();
	}
}
