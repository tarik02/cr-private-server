package royaleserver.network.protocol.client.commands;

import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public final class ChestBuy extends ClientCommand {
	public static final short ID = Commands.CHEST_BUY;

	public int tickStart;
	public int tickEnd;
	public long accountId;

	public byte unknown_0;

	public ChestBuy() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		accountId = stream.getRrsLong();
		unknown_0 = stream.getByte();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleChestBuy(this);
	}
}
