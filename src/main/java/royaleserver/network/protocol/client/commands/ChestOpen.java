package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public class ChestOpen extends ClientCommand {
	public static final short ID = Commands.CHEST_OPEN;

	public int tickStart;
	public int tickEnd;

	public int unknown_0;
	public int unknown_1;

	public int slot;

	public ChestOpen() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		unknown_0 = stream.getRrsInt32();
		unknown_1 = stream.getRrsInt32();

		slot = stream.getRrsInt32();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleChestOpen(this);
	}
}
