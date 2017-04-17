package com.tarik02.clashroyale.server.protocol.messages.client;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Message;
import com.tarik02.clashroyale.server.utils.DataStream;

public class DonateAllianceUnit extends Message {
	public static final short ID = Info.DONATE_ALLIANCE_UNIT;

	public byte type;
	public byte spell;
	public long streamID;

	public DonateAllianceUnit() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(type);
		stream.putByte(spell);
		stream.putBLong(streamID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		type = stream.getByte();
		spell = stream.getByte();
		streamID = stream.getBLong();
	}
}
