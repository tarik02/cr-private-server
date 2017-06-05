package royaleserver.network.protocol.client;

import royaleserver.network.protocol.client.commands.*;
import royaleserver.network.protocol.server.commands.NameSet;

public interface ClientCommandHandler {
	boolean handleChallengeBuy(ChallengeBuy command) throws Throwable;
	boolean handleChestBuy(ChestBuy command) throws Throwable;
	boolean handleChestOpen(ChestOpen command) throws Throwable;
	boolean handleDeckChangeCard(DeckChangeCard command) throws Throwable;
	boolean handleFightStart(FightStart command) throws Throwable;
}
