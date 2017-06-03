package royaleserver.network.protocol.server;

import royaleserver.network.protocol.Command;
import royaleserver.utils.DataStream;

public abstract class ServerCommand extends Command {
	public ServerCommand(short id) {
		super(id);
	}

	public abstract void encode(DataStream stream);
}
