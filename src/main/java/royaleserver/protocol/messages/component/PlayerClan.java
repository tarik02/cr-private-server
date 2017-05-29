package royaleserver.protocol.messages.component;

import royaleserver.database.entity.ClanEntity;
import royaleserver.database.entity.ClanRole;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class PlayerClan extends Component {
	public static final int ROLE_MEMBER = 1;
	public static final int ROLE_ELDER = 2;
	public static final int ROLE_COLEADER = 3;
	public static final int ROLE_LEADER = 4;

	public long id;
	public String name;
	public SCID badge;
	public int role;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsLong(id);
		stream.putString(name);
		stream.putRrsInt32((int)(badge.getValue() + 1));
		stream.putRrsInt32(role);
	}

	public static PlayerClan from(PlayerEntity entity) {
		return from(entity.getClan(), entity.getClanRole());
	}

	public static PlayerClan from(ClanEntity entity, ClanRole role) {
		if (entity != null) {
			PlayerClan clan = new PlayerClan();
			clan.id = entity.getId();
			clan.name = entity.getName();
			clan.badge = entity.getLogicBadge().getScid();

			switch (role) {
			case MEMBER:
				clan.role = ROLE_MEMBER;
				break;
			case ELDER:
				clan.role = ROLE_ELDER;
				break;
			case CO_LEADER:
				clan.role = ROLE_COLEADER;
				break;
			case LEADER:
				clan.role = ROLE_LEADER;
				break;
			}

			return clan;
		}

		return null;
	}
}
