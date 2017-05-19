package royaleserver.protocol.messages.command;

import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.utils.DataStream;

public class BuyChallenge extends Command {
	public static final short ID = 537;

	public BuyChallenge() {
		super(ID);
	}

	@Override
	public void decode(DataStream stream) {
		super.decode(stream);
	}

	@Override
	public boolean handle(CommandHandler handler) throws Throwable {
		//return handler.handleBuyChallengeCommand(this);
		return false;
	}
}
