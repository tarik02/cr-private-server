package royaleserver.protocol.messages;

import royaleserver.protocol.messages.command.*;

public interface CommandHandler {
    boolean handleBuyChestCommand(BuyChest command) throws Throwable;
    boolean handleOpenChestCommand(OpenChest command) throws Throwable;

	boolean handleNextCardChestCommand(NextCardChest command) throws Throwable;

	boolean handleStartFight(StartFight command) throws Throwable;
	boolean handleSetNickname(SetNickname command) throws Throwable;

	boolean handleChangeDeckCardCommand(ChangeDeckCard command) throws Throwable;
}
