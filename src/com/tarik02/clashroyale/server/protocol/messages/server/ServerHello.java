package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class ServerHello extends Message {
	public static final short ID = Info.SERVER_HELLO;

	public String sessionKey;

	public ServerHello() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(sessionKey);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		sessionKey = stream.getString();
	}
}
