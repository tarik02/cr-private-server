package royaleserver.network.protocol.server.components;

import royaleserver.logic.ClanBadge;
import royaleserver.logic.ClanRole;
import royaleserver.protocol.messages.Component;
import royaleserver.utils.DataStream;

public final class PlayerClan extends Component {
	public long id;
	public String name;
	public ClanBadge badge;
	public ClanRole role;

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);

		stream.putRrsLong(id);
		stream.putString(name);
		stream.putRrsInt32(badge.getScid().getLow() + 1);
		stream.putRrsInt32(role.getIndex());
	}
}
