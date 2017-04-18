package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

public class BattleStreamEntry extends Component {
	public byte unknown_0;
	public int unknown_1;
	public int unknown_2;
	public byte unknown_3;
	public long winnerID;
	public String winnerName;
	public byte unknown_6;
	public int ageSeconds;
	public byte unknown_8;
	public String json;
	public byte unknown_10;
	public byte unknown_11;
	public int unknown_12;
	public byte unknown_13;

	public BattleStreamEntry() {
		unknown_0 = 0;
		unknown_1 = 0;
		unknown_2 = 0;
		unknown_3 = 0;
		winnerID = 0;
		winnerName = "";
		unknown_6 = 0;
		ageSeconds = 0;
		unknown_8 = 0;
		json = "";
		unknown_10 = 0;
		unknown_11 = 0;
		unknown_12 = 0;
		unknown_13 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putByte(unknown_0);
		stream.putBInt(unknown_1);
		stream.putBInt(unknown_2);
		stream.putByte(unknown_3);
		stream.putBLong(winnerID);
		stream.putString(winnerName);
		stream.putByte(unknown_6);
		stream.putRrsInt32(ageSeconds);
		stream.putByte(unknown_8);
		stream.putString(json);
		stream.putByte(unknown_10);
		stream.putByte(unknown_11);
		stream.putVarInt32(unknown_12);
		stream.putByte(unknown_13);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		unknown_0 = stream.getByte();
		unknown_1 = stream.getBInt();
		unknown_2 = stream.getBInt();
		unknown_3 = stream.getByte();
		winnerID = stream.getBLong();
		winnerName = stream.getString();
		unknown_6 = stream.getByte();
		ageSeconds = stream.getRrsInt32();
		unknown_8 = stream.getByte();
		json = stream.getString();
		unknown_10 = stream.getByte();
		unknown_11 = stream.getByte();
		unknown_12 = stream.getVarInt32();
		unknown_13 = stream.getByte();
	}
}
