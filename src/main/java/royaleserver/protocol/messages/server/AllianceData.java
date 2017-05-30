package royaleserver.protocol.messages.server;

import royaleserver.database.entity.ClanEntity;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.protocol.Info;
import royaleserver.protocol.messages.Message;
import royaleserver.protocol.messages.component.AllianceHeaderEntry;
import royaleserver.protocol.messages.component.AllianceMemberEntry;
import royaleserver.utils.DataStream;

public class AllianceData extends Message {
	public static final short ID = Info.ALLIANCE_DATA;

	public AllianceHeaderEntry header;
	public String description;
	public AllianceMemberEntry[] members;
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

		header = new AllianceHeaderEntry();
		description = "";
		members = new AllianceMemberEntry[0];
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

		header.encode(stream);
		stream.putString(description);
		members = new AllianceMemberEntry[stream.getRrsInt32()];
		for (int i = 0; i < members.length; ++i) {
			members[i].encode(stream);
		}
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

		header.decode(stream);
		description = stream.getString();
		stream.putRrsInt32(members.length);
		for (int i = 0; i < members.length; ++i) {
			members[i] = new AllianceMemberEntry();
			members[i].decode(stream);
		}
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

	public static AllianceData from(ClanEntity entity) {
		AllianceData allianceData = new AllianceData();
		allianceData.header = AllianceHeaderEntry.from(entity);
		allianceData.description = entity.getDescription();
		allianceData.members = new AllianceMemberEntry[entity.getMembers().size()];

		int i = 0;
		for (PlayerEntity member : entity.getMembers()) {
			allianceData.members[i++] = AllianceMemberEntry.from(member);
		}

		return allianceData;
	}
}
