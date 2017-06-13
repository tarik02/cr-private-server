package royaleserver.network.protocol.server.components;

import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class ClanMember {
	public long avatarId;
	public String name;
	public SCID arena;
	public byte role;
	public int expLevel;
	public int score;
	public int donations;
	public int unknown_8;
	public byte currentRank;
	public byte previousRank;
	public int clanChestCrowns;
	public int unknown_12;
	public int unknown_13;
	public int unknown_14;
	public byte unknown_15;
	public byte unknown_16;
	public long homeID;

	public void encode(DataStream stream) {
		stream.putBLong(avatarId);
		stream.putString(name);
		stream.putSCID(arena);
		stream.putByte(role);
		stream.putRrsInt32(expLevel);
		stream.putRrsInt32(score);
		stream.putRrsInt32(donations);
		stream.putVarInt32(unknown_8);
		stream.putByte(currentRank);
		stream.putByte(previousRank);
		stream.putRrsInt32(clanChestCrowns);
		stream.putRrsInt32(unknown_12);
		stream.putRrsInt32(unknown_13);
		stream.putRrsInt32(unknown_14);
		stream.putByte(unknown_15);
		stream.putByte(unknown_16);
		stream.putBLong(homeID);
	}
}
