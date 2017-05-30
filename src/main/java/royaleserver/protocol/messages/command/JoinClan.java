package royaleserver.protocol.messages.command;

import royaleserver.database.entity.ClanEntity;
import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class JoinClan extends Command {
	public static final short ID = 206;

	public long allianceId;
	public String name;
	public SCID badge;
	public byte unknown_0;
	public byte accepted;

	public JoinClan() {
		super(ID);

		allianceId = 0;
		name = "";
		badge = new SCID(16, 0);
		unknown_0 = 0;
		accepted = 0;
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putBLong(allianceId);
		stream.putString(name);
		stream.putSCID(badge);
		stream.putByte(unknown_0);
		stream.putByte(accepted);
	}

	public static JoinClan from(ClanEntity entity) {
		JoinClan joinClan = new JoinClan();
		joinClan.allianceId = entity.getId();
		joinClan.name = entity.getName();
		joinClan.badge = new SCID(entity.getBadge().getId());
		joinClan.unknown_0 = 0;
		joinClan.accepted = 1;

		return joinClan;
	}
}
