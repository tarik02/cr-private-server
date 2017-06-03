package royaleserver.protocol.messages.server;

import royaleserver.database.entity.ClanEntity;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.protocol.Info;
import royaleserver.network.protocol.Message;
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
		stream.putRrsInt32(members.length);
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

	public static AllianceData from(ClanEntity entity) {
		AllianceData allianceData = new AllianceData();
		allianceData.header = AllianceHeaderEntry.from(entity);
		allianceData.description = entity.getDescription();
		allianceData.members = new AllianceMemberEntry[entity.getMembers().size()];

		int i = 0;
		for (PlayerEntity member : entity.getMembers()) {
			allianceData.members[i++] = AllianceMemberEntry.from(member);
		}

		allianceData.unknown_3 = 1; // 1
		allianceData.unknown_4 = 0;
		allianceData.unknown_5 = 234815; // 234815
		allianceData.unknown_6 = 0;
		allianceData.unknown_7 = 1494573487; // timestamp last request?
		allianceData.unknown_8 = 1494832687; // timestamp ?
		allianceData.unknown_9 = 4179457; // 4179457
		allianceData.unknown_10 = 0;
		allianceData.unknown_11 = 1;

		return allianceData;
	}
}
