package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class JoinAlliance extends Message {
	public static final short ID = Info.JOIN_ALLIANCE;

	public long allianceId;
	public byte unknown_1;
	public byte unknown_2;
	public byte unknown_3;
	public byte unknown_4;

	public JoinAlliance() {
		super(ID);

		allianceId = 0;
		unknown_1 = 0;
		unknown_2 = 0;
		unknown_3 = 0;
		unknown_4 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(allianceId);
		stream.putByte(unknown_1);
		stream.putByte(unknown_2);
		stream.putByte(unknown_3);
		stream.putByte(unknown_4);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		allianceId = stream.getBLong();
		unknown_1 = stream.getByte();
		unknown_2 = stream.getByte();
		unknown_3 = stream.getByte();
		unknown_4 = stream.getByte();
	}
}
