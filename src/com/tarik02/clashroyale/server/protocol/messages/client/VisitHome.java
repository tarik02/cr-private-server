package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Handler;
import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class VisitHome extends Message {
	public static final short ID = Info.VISIT_HOME;

	public long accountID;

	public VisitHome() {
		super(ID);

		accountID = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(accountID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		accountID = stream.getBLong();
	}

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleVisitHome(this);
	}
}