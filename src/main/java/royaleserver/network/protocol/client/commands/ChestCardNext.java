package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public final class ChestCardNext extends ClientCommand {
	public static final short ID = Commands.CHEST_BUY;

	public ChestCardNext() {
		super(ID);
	}

	@Override
	public ClientCommand create() {
		return new ChestCardNext();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleChestCardNext(this);
	}

	@Override
	public void decode(DataStream stream) {
	}
}
