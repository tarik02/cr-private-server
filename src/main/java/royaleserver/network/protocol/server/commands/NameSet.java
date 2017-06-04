package royaleserver.network.protocol.server.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.server.ServerCommand;
import royaleserver.utils.DataStream;

public final class NameSet extends ServerCommand {
	public static final short ID = Commands.NAME_SET;

	public String name;

	public NameSet() {
		super(ID);
	}

	@Override
	public ServerCommand create() {
		return new NameSet();
	}

	@Override
	public void encode(DataStream stream) {
		stream.putString(name);

		stream.putRrsInt32(0);
		stream.putRrsInt32(0);
		stream.putRrsInt32(0);

		stream.putRrsInt32(1);
		stream.putRrsInt32(1);
		stream.putRrsInt32(1);
	}
}
