package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class ShutdownStarted extends Message {
	public static final short ID = Info.SHUTDOWN_STARTED;

	public int delay;

	public ShutdownStarted() {
		super(ID);

		delay = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(delay);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		delay = stream.getRrsInt32();
	}
}
