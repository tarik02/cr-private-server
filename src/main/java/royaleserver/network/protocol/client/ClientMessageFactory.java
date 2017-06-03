package royaleserver.network.protocol.client;

import royaleserver.network.protocol.Factory;

public final class ClientMessageFactory extends Factory<Short, ClientMessage> {
	public static final ClientMessageFactory instance = new ClientMessageFactory();

	private ClientMessageFactory() {
		super("royaleserver.network.protocol.client.message", ClientMessage.class);
	}
}
