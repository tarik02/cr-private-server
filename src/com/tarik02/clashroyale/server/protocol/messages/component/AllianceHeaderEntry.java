package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.utils.SCID;

public class AllianceHeaderEntry extends Component {
	public long Id;
	public String name;
	public SCID badge;
	public byte type;
	public byte numberOfMembers;
	public int score;
	public int requiredScore;
	public byte unknown_7;
	public byte unknown_8;
	public int currenRank;
	public int unknown_10;
	public int donations;
	public int unknown_12;
	public byte unknown_13;
	public byte unknown_14;
	public byte unknown_15;
	public int region;
	public byte unknown_17;

	public AllianceHeaderEntry() {
		Id = 0;
		name = "";
		badge = new SCID();
		type = 0;
		numberOfMembers = 0;
		score = 0;
		requiredScore = 0;
		unknown_7 = 0;
		unknown_8 = 0;
		currenRank = 0;
		unknown_10 = 0;
		donations = 0;
		unknown_12 = 0;
		unknown_13 = 0;
		unknown_14 = 0;
		unknown_15 = 0;
		region = 0;
		unknown_17 = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(Id);
		stream.putString(name);
		stream.putSCID(badge);
		stream.putByte(type);
		stream.putByte(numberOfMembers);
		stream.putRrsInt32(score);
		stream.putRrsInt32(requiredScore);
		stream.putByte(unknown_7);
		stream.putByte(unknown_8);
		stream.putRrsInt32(currenRank);
		stream.putRrsInt32(unknown_10);
		stream.putRrsInt32(donations);
		stream.putRrsInt32(unknown_12);
		stream.putByte(unknown_13);
		stream.putByte(unknown_14);
		stream.putByte(unknown_15);
		stream.putRrsInt32(region);
		stream.putByte(unknown_17);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		Id = stream.getBLong();
		name = stream.getString();
		badge = stream.getSCID();
		type = stream.getByte();
		numberOfMembers = stream.getByte();
		score = stream.getRrsInt32();
		requiredScore = stream.getRrsInt32();
		unknown_7 = stream.getByte();
		unknown_8 = stream.getByte();
		currenRank = stream.getRrsInt32();
		unknown_10 = stream.getRrsInt32();
		donations = stream.getRrsInt32();
		unknown_12 = stream.getRrsInt32();
		unknown_13 = stream.getByte();
		unknown_14 = stream.getByte();
		unknown_15 = stream.getByte();
		region = stream.getRrsInt32();
		unknown_17 = stream.getByte();
	}
}
