package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class HomeAskData extends ClientMessage {
	public static final short ID = Messages.HOME_ASK_DATA;

	public long accountId;

	public HomeAskData() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new HomeAskData();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleHomeAskData(this);
	}

	@Override
	public void decode(DataStream stream) {
		accountId = stream.getBLong();
	}
}
