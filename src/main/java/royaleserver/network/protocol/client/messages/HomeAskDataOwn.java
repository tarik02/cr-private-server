package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class HomeAskDataOwn extends ClientMessage {
	public static final short ID = Messages.HOME_ASK_DATA_OWN;

	public HomeAskDataOwn() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new HomeAskDataOwn();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleHomeAskDataOwn(this);
	}

	@Override
	public void decode(DataStream stream) {
	}
}
