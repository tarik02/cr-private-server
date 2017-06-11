package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public final class DeckChange extends ClientCommand {
	public static final short ID = Commands.DECK_CHANGE;

	public int tickStart;
	public int tickEnd;

	public int unknown_2;
	public int unknown_3;

	public int slot;

	public DeckChange() {
		super(ID);
	}

	@Override
	public ClientCommand create() {
		return new DeckChange();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleDeckChange(this);
	}

	@Override
	public void decode(DataStream stream) {
		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		unknown_2 = stream.getRrsInt32();
		unknown_3 = stream.getRrsInt32();

		slot = stream.getRrsInt32();
	}
}
