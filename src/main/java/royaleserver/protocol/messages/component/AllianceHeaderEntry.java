package royaleserver.protocol.messages.component;

import royaleserver.database.entity.ClanEntity;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class AllianceHeaderEntry extends Component {
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
		id = 0;
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

		stream.putBLong(id);
		stream.putString(name);
		stream.putSCID(badge);
		stream.putRrsInt32(type);
		stream.putRrsInt32(numberOfMembers);
		stream.putRrsInt32(score);
		stream.putRrsInt32(requiredScore);
		stream.putRrsInt32(unknown_7);
		stream.putRrsInt32(unknown_8);
		stream.putRrsInt32(currenRank);
		stream.putRrsInt32(unknown_10);
		stream.putRrsInt32(donations);
		stream.putRrsInt32(unknown_12);
		stream.putRrsInt32(unknown_13);
		stream.putRrsInt32(unknown_14);
		stream.putRrsInt32(unknown_15);
		stream.putRrsInt32(region);
		stream.putRrsInt32(unknown_17);

		// next clan
		stream.putByte((byte) 0);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		id = stream.getBLong();
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

	public static AllianceHeaderEntry from(ClanEntity entity) {
		AllianceHeaderEntry response = new AllianceHeaderEntry();
		response.id = entity.getId();
		response.name = entity.getName();
		response.badge = entity.getBadge();

		switch (entity.getType()) {
		case OPEN:
			response.type = CLAN_OPEN;
			break;
		case INVITE:
			response.type = CLAN_INVITE;
			break;
		case CLOSED:
			response.type = CLAN_CLOSED;
			break;
		}

		response.numberOfMembers = entity.getMembers().size();
		response.score = entity.getScore();
		response.requiredScore = entity.getRequiredTrophies();
		response.unknown_7 = 0;
		response.unknown_8 = 0;
		response.currenRank = 0;
		response.unknown_10 = 0;
		response.donations = entity.getDonationsPerWeek();
		response.unknown_12 = 0;
		response.unknown_13 = 0;
		response.unknown_14 = 1;
		response.unknown_15 = 12;
		response.region = 57;
		response.unknown_17 = 6;

		return response;
	}
}
