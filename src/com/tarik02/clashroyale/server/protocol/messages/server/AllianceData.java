package com.tarik02.clashroyale.server.protocol.messages.server;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class AllianceData extends Message {
	public static final short ID = Info.ALLIANCE_DATA;

	public String description;
	public byte unknown_3;
	public byte unknown_4;
	public int unknown_5;
	public int unknown_6;
	public int unknown_7;
	public int unknown_8;
	public int unknown_9;
	public byte unknown_10;
	public byte unknown_11;

	public AllianceData() {
		super(ID);

		description = "";
		unknown_3 = 0;
		unknown_4 = 0;
		unknown_5 = 0;
		unknown_6 = 0;
		unknown_7 = 0;
		unknown_8 = 0;
		unknown_9 = 0;
		unknown_10 = 0;
		unknown_11 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putString(description);
		stream.putByte(unknown_3);
		stream.putByte(unknown_4);
		stream.putRrsInt32(unknown_5);
		stream.putRrsInt32(unknown_6);
		stream.putBInt(unknown_7);
		stream.putBInt(unknown_8);
		stream.putRrsInt32(unknown_9);
		stream.putByte(unknown_10);
		stream.putByte(unknown_11);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		description = stream.getString();
		unknown_3 = stream.getByte();
		unknown_4 = stream.getByte();
		unknown_5 = stream.getRrsInt32();
		unknown_6 = stream.getRrsInt32();
		unknown_7 = stream.getBInt();
		unknown_8 = stream.getBInt();
		unknown_9 = stream.getRrsInt32();
		unknown_10 = stream.getByte();
		unknown_11 = stream.getByte();
	}
}
