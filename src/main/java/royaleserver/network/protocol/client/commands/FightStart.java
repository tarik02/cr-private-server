package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public final class FightStart extends ClientCommand {
	public static final short ID = Commands.FIGHT_START;

	public FightStart() {
		super(ID);
	}

	@Override
	public ClientCommand create() {
		return new FightStart();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleFightStart(this);
	}

	@Override
	public void decode(DataStream stream) {
	}
}
