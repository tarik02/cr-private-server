package royaleserver.network.protocol.server.components;

import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class ClanHeader {
	public static final byte CLAN_OPEN = 1;
	public static final byte CLAN_INVITE = 2;
	public static final byte CLAN_CLOSED = 3;

	public long id;
	public String name;
	public SCID badge;
	public byte type;
	public int numberOfMembers;
	public int score;
	public int requiredScore;
	public byte unknown_7;
	public byte unknown_8;
	public int currentRank;
	public int unknown_10;
	public int donations;
	public int unknown_12;
	public byte unknown_13;
	public byte unknown_14;
	public byte unknown_15;
	public int region;
	public byte unknown_17;

	public void encode(DataStream stream) {
		stream.putBLong(id);
		stream.putString(name);
		stream.putSCID(badge);
		stream.putByte(type);
		stream.putByte((byte)numberOfMembers);
		stream.putRrsInt32(score);
		stream.putRrsInt32(requiredScore);
		stream.putByte(unknown_7);
		stream.putByte(unknown_8);
		stream.putRrsInt32(currentRank);
		stream.putRrsInt32(unknown_10);
		stream.putRrsInt32(donations);
		stream.putRrsInt32(unknown_12);
		stream.putByte(unknown_13);
		stream.putByte(unknown_14);
		stream.putByte(unknown_15);
		stream.putRrsInt32(region);
		stream.putByte(unknown_17);
	}
}
