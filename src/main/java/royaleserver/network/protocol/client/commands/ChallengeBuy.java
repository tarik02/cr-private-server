package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public final class ChallengeBuy extends ClientCommand {
	public static final short ID = Commands.CHALLENGE_BUY;

	public ChallengeBuy() {
		super(ID);
	}

	@Override
	public ClientCommand create() {
		return new ChallengeBuy();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleChallengeBuy(this);
	}

	@Override
	public void decode(DataStream stream) {
	}
}
