package royaleserver.protocol.messages;

import royaleserver.protocol.messages.command.BuyChest;
import royaleserver.protocol.messages.command.OpenChest;
import royaleserver.protocol.messages.command.SetNickname;
import royaleserver.protocol.messages.command.StartFight;

public interface CommandHandler {
    boolean handleBuyChestCommand(BuyChest command) throws Throwable;
    boolean handleOpenChestCommand(OpenChest command) throws Throwable;

	boolean handleStartFight(StartFight command) throws Throwable;
	boolean handleSetNickname(SetNickname command) throws Throwable;
}
