package royaleserver.network.protocol.server;

import royaleserver.network.protocol.Factory;
import royaleserver.network.protocol.client.ClientMessage;

public final class ServerMessageFactory extends Factory<Short, ServerMessage> {
	public static final ServerMessageFactory instance = new ServerMessageFactory();

	private ServerMessageFactory() {
		super("royaleserver.network.protocol.client.messages", ServerMessage.class);
	}
}
