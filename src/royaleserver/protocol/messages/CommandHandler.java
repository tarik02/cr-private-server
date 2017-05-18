package royaleserver.protocol.messages;

import royaleserver.protocol.messages.command.BuyChest;
import royaleserver.protocol.messages.command.OpenChest;

public interface CommandHandler {
    boolean handleBuyChestCommand(BuyChest command) throws Throwable;
    boolean handleOpenChestCommand(OpenChest command) throws Throwable;
}
