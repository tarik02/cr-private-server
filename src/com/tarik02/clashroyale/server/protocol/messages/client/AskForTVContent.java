package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.utils.SCID;

public class AskForTVContent extends Message {
	public static final short ID = Info.ASK_FOR_TV_CONTENT;

	public SCID arena;

	public AskForTVContent() {
		super(ID);

		arena = new SCID();
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putSCID(arena);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		arena = stream.getSCID();
	}
}
