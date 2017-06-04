package royaleserver.network.protocol.server.commands;

import royaleserver.database.entity.ClanEntity;
import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.server.ServerCommand;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public final class ClanJoinOk extends ServerCommand {
	public static final short ID = Commands.CLAN_JOIN_OK;

	public long allianceId;
	public String name;
	public SCID badge;
	public byte unknown_3;
	public byte accepted;

	public ClanJoinOk() {
		super(ID);
	}

	@Override
	public ServerCommand create() {
		return new ClanJoinOk();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putBLong(allianceId);
		stream.putString(name);
		stream.putSCID(badge);
		stream.putByte(unknown_3);
		stream.putByte(accepted);
	}

	public static ClanJoinOk from(ClanEntity entity) {
		ClanJoinOk joinClan = new ClanJoinOk();
		joinClan.allianceId = entity.getId();
		joinClan.name = entity.getName();
		joinClan.badge = entity.getLogicBadge().getScid();
		joinClan.unknown_3 = 1;
		joinClan.accepted = 1;

		return joinClan;
	}
}
