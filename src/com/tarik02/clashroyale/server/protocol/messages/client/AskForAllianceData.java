package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Handler;
import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AskForAllianceData extends Message {
	public static final short ID = Info.ASK_FOR_ALLIANCE_DATA;

	public long allianceId;

	public AskForAllianceData() {
		super(ID);

		allianceId = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(allianceId);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		allianceId = stream.getBLong();
	}

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleAskForAllianceData(this);
	}
}
