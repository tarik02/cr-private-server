package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class InboxAsk extends ClientMessage {
	public static final short ID = Messages.INBOX_ASK;

	public InboxAsk() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new InboxAsk();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleInboxAsk(this);
	}

	@Override
	public void decode(DataStream stream) {
	}
}
