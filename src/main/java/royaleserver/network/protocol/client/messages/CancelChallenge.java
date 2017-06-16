package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.utils.DataStream;

public class CancelChallenge extends ClientMessage {
	public static final short ID = Messages.CANCEL_CHALLENGE_REQUEST;

	public CancelChallenge() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new CancelChallenge();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleCancelChallenge(this);
	}

	@Override
	public void decode(DataStream stream) {
	}

}
