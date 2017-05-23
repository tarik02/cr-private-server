package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.protocol.messages.component.ChestItem;
import royaleserver.utils.DataStream;
import royaleserver.utils.Hex;

public class StartFight extends Command {

	public static final short ID = 525;

	public StartFight() {
		super(ID);
	}

	@Override
	public void encode(DataStream stream) {
		super.encode(stream);
	}

	@Override
	public boolean handle(CommandHandler handler) throws Throwable {
		return handler.handleStartFight(this);
	}
}
