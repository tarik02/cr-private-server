package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.network.protocol.server.components.ClanHeader;
import royaleserver.network.protocol.server.components.ClanMember;
import royaleserver.utils.DataStream;

public final class ClanData extends ServerMessage {
	public static final short ID = Messages.CLAN_DATA;

	public ClanHeader header;
	public String description;
	public ClanMember[] members;
	public byte unknown_3;
	public byte unknown_4;
	public int unknown_5;
	public int unknown_6;
	public int unknown_7;
	public int unknown_8;
	public int unknown_9;
	public byte unknown_10;
	public byte unknown_11;

	public ClanData() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new ClanData();
	}

	@Override
	public void encode(DataStream stream) {
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
}
