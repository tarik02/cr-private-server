package royaleserver.network.protocol.client;

import royaleserver.network.protocol.Factory;

public final class ClientCommandFactory extends Factory<Short, ClientCommand> {
	public static final ClientCommandFactory instance = new ClientCommandFactory();

	private ClientCommandFactory() {
		super("royaleserver.network.protocol.client.command", ClientCommand.class);
	}
}
