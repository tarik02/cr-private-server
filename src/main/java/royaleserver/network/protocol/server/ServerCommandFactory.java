package royaleserver.network.protocol.server;

import royaleserver.network.protocol.Factory;
import royaleserver.network.protocol.client.ClientCommand;

public final class ServerCommandFactory extends Factory<Short, ServerCommand> {
	public static final ServerCommandFactory instance = new ServerCommandFactory();

	private ServerCommandFactory() {
		super("royaleserver.network.protocol.server.command", ServerCommand.class);
	}
}
