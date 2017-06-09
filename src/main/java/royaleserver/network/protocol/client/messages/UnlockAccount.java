package royaleserver.network.protocol.client.messages;

import royaleserver.network.protocol.client.ClientMessage;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.Messages;
import royaleserver.utils.DataStream;

public final class UnlockAccount extends ClientMessage {
	public static final short ID = Messages.TOURNAMENT_ASK_JOINABLE;

	public int unknown_0;
	public int unknown_1;

	public String deviceToken;
	public String unlockCode;

	public UnlockAccount() {
		super(ID);
	}

	@Override
	public ClientMessage create() {
		return new TournamentAskJoinable();
	}

	@Override
	public boolean handle(ClientMessageHandler handler) throws Throwable {
		return handler.handleUnlockAccount(this);
	}

	@Override
	public void decode(DataStream stream) {
		unknown_0 = stream.getBInt();
		unknown_1 = stream.getBInt();

		deviceToken = stream.getString();
		unlockCode = stream.getString();
	}
}
