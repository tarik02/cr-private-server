package royaleserver.network.protocol.client.commands;

import royaleserver.logic.Chest;
import royaleserver.network.protocol.Commands;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.utils.DataStream;

public final class ChestBuy extends ClientCommand {
	public static final short ID = Commands.CHEST_BUY;

	public int tickStart;
	public int tickEnd;
	public long accountId;

	public byte unknown_3;
	public Chest chest;

	public ChestBuy() {
		super(ID);
	}

	@Override
	public ClientCommand create() {
		return new ChestBuy();
	}

	@Override
	public boolean handle(ClientCommandHandler handler) throws Throwable {
		return handler.handleChestBuy(this);
	}

	@Override
	public void decode(DataStream stream) {
		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		accountId = stream.getRrsLong();
		unknown_3 = stream.getByte();

		chest = chest.by(stream.getRrsInt32());
	}
}
