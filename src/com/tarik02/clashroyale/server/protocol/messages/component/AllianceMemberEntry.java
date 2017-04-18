package com.tarik02.clashroyale.server.protocol.messages.component;

import com.tarik02.clashroyale.server.protocol.Info;
import com.tarik02.clashroyale.server.protocol.messages.Component;
import com.tarik02.clashroyale.server.utils.DataStream;

import com.tarik02.clashroyale.server.utils.SCID;

public class AllianceMemberEntry extends Component {
	public long avatarId;
	public String facebookId;
	public String name;
	public SCID arena;
	public byte role;
	public int expLevel;
	public int score;
	public int donations;
	public int unknown_8;
	public byte currenRank;
	public byte previousRank;
	public int clanChestCrowns;
	public int unknown_12;
	public int unknown_13;
	public int unknown_14;
	public byte unknown_15;
	public byte unknown_16;
	public long homeID;

	public AllianceMemberEntry() {
		avatarId = 0;
		facebookId = "";
		name = "";
		arena = new SCID();
		role = 0;
		expLevel = 0;
		score = 0;
		donations = 0;
		unknown_8 = 0;
		currenRank = 0;
		previousRank = 0;
		clanChestCrowns = 0;
		unknown_12 = 0;
		unknown_13 = 0;
		unknown_14 = 0;
		unknown_15 = 0;
		unknown_16 = 0;
		homeID = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(avatarId);
		stream.putString(facebookId);
		stream.putString(name);
		stream.putSCID(arena);
		stream.putByte(role);
		stream.putRrsInt32(expLevel);
		stream.putRrsInt32(score);
		stream.putRrsInt32(donations);
		stream.putVarInt32(unknown_8);
		stream.putByte(currenRank);
		stream.putByte(previousRank);
		stream.putRrsInt32(clanChestCrowns);
		stream.putRrsInt32(unknown_12);
		stream.putRrsInt32(unknown_13);
		stream.putRrsInt32(unknown_14);
		stream.putByte(unknown_15);
		stream.putByte(unknown_16);
		stream.putBLong(homeID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		avatarId = stream.getBLong();
		facebookId = stream.getString();
		name = stream.getString();
		arena = stream.getSCID();
		role = stream.getByte();
		expLevel = stream.getRrsInt32();
		score = stream.getRrsInt32();
		donations = stream.getRrsInt32();
		unknown_8 = stream.getVarInt32();
		currenRank = stream.getByte();
		previousRank = stream.getByte();
		clanChestCrowns = stream.getRrsInt32();
		unknown_12 = stream.getRrsInt32();
		unknown_13 = stream.getRrsInt32();
		unknown_14 = stream.getRrsInt32();
		unknown_15 = stream.getByte();
		unknown_16 = stream.getByte();
		homeID = stream.getBLong();
	}
}
