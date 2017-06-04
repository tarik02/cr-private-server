package royaleserver.network.protocol.server.components;

import royaleserver.logic.ClanBadge;
import royaleserver.logic.ClanRole;
import royaleserver.utils.DataStream;

public final class PlayerClan {
	public long id;
	public String name;
	public ClanBadge badge;
	public ClanRole role;

	public void encode(DataStream stream) {
		stream.putRrsLong(id);
		stream.putString(name);
		stream.putRrsInt32(badge.getScid().getLow() + 1);
		stream.putRrsInt32(role.getIndex());
	}
}
