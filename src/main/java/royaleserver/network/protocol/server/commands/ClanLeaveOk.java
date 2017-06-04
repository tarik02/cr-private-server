package royaleserver.network.protocol.server.commands;

import royaleserver.database.entity.ClanEntity;
import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.server.ServerCommand;
import royaleserver.utils.DataStream;

public final class ClanLeaveOk extends ServerCommand {
	public static final short ID = Commands.CLAN_LEAVE_OK;

	public long allianceId;

	public ClanLeaveOk() {
		super(ID);
	}

	@Override
	public ServerCommand create() {
		return new ClanLeaveOk();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putBLong(allianceId);

		stream.putRrsInt32(0);
		stream.putRrsInt32(4);

		stream.putByte((byte)127);
		stream.putByte((byte)127);

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
	}

	public static ClanLeaveOk from(ClanEntity entity) {
		ClanLeaveOk leaveClanOK = new ClanLeaveOk();
		leaveClanOK.allianceId = entity.getId();

		return leaveClanOK;
	}
}
