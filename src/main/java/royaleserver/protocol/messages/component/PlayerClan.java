package royaleserver.protocol.messages.component;

import royaleserver.database.entity.ClanEntity;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.logic.ClanRole;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;
import royaleserver.utils.SCID;

public class PlayerClan extends Component {
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
		ClanEntity clan = entity.getClan();
		if (clan == null) {
			return null;
		}

		return from(clan, entity.getLogicClanRole());
	}

	public static PlayerClan from(ClanEntity entity, ClanRole role) {
		if (entity != null) {
			PlayerClan clan = new PlayerClan();
			clan.id = entity.getId();
			clan.name = entity.getName();
			clan.badge = entity.getLogicBadge().getScid();

			clan.role = role.getIndex();

			return clan;
		}

		return null;
	}
}
