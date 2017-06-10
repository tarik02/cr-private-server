package royaleserver.network.protocol.server.messages;

import royaleserver.network.protocol.Messages;
import royaleserver.network.protocol.server.ServerMessage;
import royaleserver.utils.DataStream;

public final class AccountUnlockFailed extends ServerMessage {
	public static final short ID = Messages.ACCOUNT_UNLOCK_FAILED;

	public AccountUnlockFailed() {
		super(ID);
	}

	@Override
	public ServerMessage create() {
		return new AccountUnlockFailed();
	}

	@Override
	public void encode(DataStream stream) {
	}
}
