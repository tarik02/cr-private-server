package royaleserver.network.protocol.server;

import royaleserver.network.protocol.*;

import royaleserver.utils.DataStream;

public abstract class ServerCommand extends Command implements FactoryTarget<ServerCommand> {
	public ServerCommand(short id) {
		super(id);
	}

	public abstract void encode(DataStream stream);
}
