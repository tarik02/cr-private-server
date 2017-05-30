package royaleserver.protocol.messages.component;

import royaleserver.database.entity.PlayerEntity;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

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

	public static AllianceMemberEntry from(PlayerEntity entity) {
		AllianceMemberEntry allianceMemberEntry = new AllianceMemberEntry();
		allianceMemberEntry.avatarId = entity.getId();
		allianceMemberEntry.facebookId = "0";
		allianceMemberEntry.name = entity.getName();
		allianceMemberEntry.arena = entity.getLogicArena().getScid();
		allianceMemberEntry.role = 4;
		allianceMemberEntry.expLevel = 7; // TODO:
		allianceMemberEntry.score = 2000; // ? - trophies
		allianceMemberEntry.donations = 0; // TODO:
		allianceMemberEntry.unknown_8 = 0;
		allianceMemberEntry.currenRank = 4; // ?
		allianceMemberEntry.previousRank = 4; // ?
		allianceMemberEntry.clanChestCrowns = 0;
		allianceMemberEntry.unknown_12 = 0;
		allianceMemberEntry.unknown_13 = -64;
		allianceMemberEntry.unknown_14 = -64;
		allianceMemberEntry.unknown_15 = 0;
		allianceMemberEntry.unknown_16 = 4;
		allianceMemberEntry.homeID = entity.getId();
		return allianceMemberEntry;
	}
}
