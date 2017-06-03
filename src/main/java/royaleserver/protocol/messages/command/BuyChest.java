package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.utils.DataStream;

public class BuyChest extends Command {
	public static final short ID = 516;

	public int tickStart;
	public int tickEnd;
	public long accountID;

	public byte unknown_3;
	public int chestID;

	public BuyChest() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		accountID = stream.getRrsLong();
		unknown_3 = stream.getByte();

		chestID = stream.getRrsInt32();
	}

	@Override
	public boolean handle(CommandHandler handler) throws Throwable {
		return handler.handleBuyChestCommand(this);
	}
}
