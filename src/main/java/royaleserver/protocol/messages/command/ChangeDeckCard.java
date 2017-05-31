package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.utils.DataStream;

public class ChangeDeckCard extends Command {
	public static final short ID = 500;

	public int tickStart;
	public int tickEnd;

	public long accountId;
	public int cardIndex;
	public int slot;

	public ChangeDeckCard() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);

		tickStart = stream.getRrsInt32();
		tickEnd = stream.getRrsInt32();

		accountId = stream.getRrsLong();
		cardIndex = stream.getVarInt32();
		slot = stream.getVarInt32();
	}

	@Override
	public boolean handle(CommandHandler handler) throws Throwable {
		return handler.handleChangeDeckCardCommand(this);
	}
}
