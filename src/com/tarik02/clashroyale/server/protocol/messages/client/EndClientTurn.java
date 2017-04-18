package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class EndClientTurn extends Message {
	public static final short ID = Info.END_CLIENT_TURN;

	public int tick;
	public int checksum;
	public byte unknown_3;
	public byte unknown_4;
	public byte unknown_5;
	public byte unknown_6;

	public EndClientTurn() {
		super(ID);

		tick = 0;
		checksum = 0;
		unknown_3 = 0;
		unknown_4 = 0;
		unknown_5 = 0;
		unknown_6 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsInt32(tick);
		stream.putRrsInt32(checksum);
		stream.putByte(unknown_3);
		stream.putByte(unknown_4);
		stream.putByte(unknown_5);
		stream.putByte(unknown_6);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		tick = stream.getRrsInt32();
		checksum = stream.getRrsInt32();
		unknown_3 = stream.getByte();
		unknown_4 = stream.getByte();
		unknown_5 = stream.getByte();
		unknown_6 = stream.getByte();
	}
}
