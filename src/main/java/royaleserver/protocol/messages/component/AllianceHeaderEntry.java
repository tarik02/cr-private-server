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
		stream.putByte(type);
		stream.putByte((byte)numberOfMembers);
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

	public static AllianceHeaderEntry from(ClanEntity entity) {
		AllianceHeaderEntry response = new AllianceHeaderEntry();
		response.id = entity.getId();
		response.name = entity.getName();
		response.badge = entity.getLogicBadge().getScid();

		/*switch (entity.getType()) {
		case OPEN:
			response.type = CLAN_OPEN;
			break;
		case INVITE:
			response.type = CLAN_INVITE;
			break;
		case CLOSED:
			response.type = CLAN_CLOSED;
			break;
		}*/

		response.type = 1;

		response.numberOfMembers = entity.getMembers().size();
		response.score = entity.getScore();
		response.requiredScore = entity.getRequiredTrophies();
		response.unknown_7 = 0;
		response.unknown_8 = 0;
		response.currenRank = 0;
		response.unknown_10 = 50; // 50
		response.donations = entity.getDonationsPerWeek();
		response.unknown_12 = 0;
		response.unknown_13 = 1;
		response.unknown_14 = 12;
		response.unknown_15 = 57;
		response.region = 6;
		response.unknown_17 = 0;

		return response;
	}
}
