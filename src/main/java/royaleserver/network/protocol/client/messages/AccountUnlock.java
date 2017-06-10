package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.utils.DataStream;

public final class AccountUnlock extends ClientMessage {
	public static final short ID = Messages.ACCOUNT_UNLOCK;

	public int unknown_0;
	public int unknown_1;

	public String deviceToken;
	public String unlockCode;

	public AccountUnlock() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new AccountUnlock();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleAccountUnlock(this);
	}

	@Override
	public void decode(DataStream stream) {
		unknown_0 = stream.getBInt();
		unknown_1 = stream.getBInt();

		deviceToken = stream.getString();
		unlockCode = stream.getString();
	}
}
