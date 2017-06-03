package royaleserver.network.protocol.client;

import royaleserver.network.protocol.Command;
import royaleserver.utils.DataStream;

public abstract class ClientCommand extends Command {
	public ClientCommand(short id) {
		super(id);
	}

	public abstract void decode(DataStream stream);
	public abstract boolean handle(ClientCommandHandler handler) throws Throwable;
}
