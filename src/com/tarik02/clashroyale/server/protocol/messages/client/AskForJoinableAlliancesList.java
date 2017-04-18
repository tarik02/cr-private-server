package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Handler;
import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AskForJoinableAlliancesList extends Message {
	public static final short ID = Info.ASK_FOR_JOINABLE_ALLIANCES_LIST;

	public AskForJoinableAlliancesList() {
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

	public boolean handle(Handler handler) throws Throwable {
		return handler.handleAskForJoinableAlliancesList(this);
	}
}
