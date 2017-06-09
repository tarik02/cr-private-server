package royaleserver.network;

import royaleserver.network.protocol.Message;

public final class UnhandledMessageException extends RuntimeException {
	public UnhandledMessageException(Message message) {
		super("The handler do not handle message " + message.getClass().getSimpleName() + ".");
	}
}
