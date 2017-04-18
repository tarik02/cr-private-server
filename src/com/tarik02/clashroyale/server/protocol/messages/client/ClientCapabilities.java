package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class ClientCapabilities extends Message {
	public static final short ID = Info.CLIENT_CAPABILITIES;

	public int ping;
	public String connectionInterface;

	public ClientCapabilities() {
		super(ID);

		ping = 0;
		connectionInterface = "";
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(ping);
		stream.putString(connectionInterface);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		ping = stream.getRrsInt32();
		connectionInterface = stream.getString();
	}
}
