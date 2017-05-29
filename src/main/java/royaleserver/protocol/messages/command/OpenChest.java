package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.utils.DataStream;

public class OpenChest extends Command {
	public static final short ID = 503;

	public int tickStart;
	public int tickEnd;

	public int unknown_0;
	public int unknown_1;

	public int slot;

	public OpenChest() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		unknown_0 = stream.getRrsInt32();
		unknown_1 = stream.getRrsInt32();

		slot = stream.getRrsInt32();
	}

	@Override
	public boolean handle(CommandHandler handler) throws Throwable {
		return handler.handleOpenChestCommand(this);
	}
}
