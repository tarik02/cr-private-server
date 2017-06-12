package royaleserver.network.protocol.client;

import royaleserver.network.protocol.client.commands.*;

public interface ClientCommandHandler {
	boolean handleCardUpgrade(CardUpgrade command) throws Throwable;
	boolean handleChallengeBuy(ChallengeBuy command) throws Throwable;
	boolean handleChestBuy(ChestBuy command) throws Throwable;
	boolean handleChestCardNext(ChestCardNext command) throws Throwable;
	boolean handleChestDraftCardSelect(ChestDraftCardSelect command) throws Throwable;
	boolean handleChestOpen(ChestOpen command) throws Throwable;
	boolean handleChestSeasonRewardOpen(ChestSeasonRewardOpen command) throws Throwable;
	boolean handleDeckChange(DeckChange command) throws Throwable;
	boolean handleDeckChangeCard(DeckChangeCard command) throws Throwable;
	boolean handleFightStart(FightStart command) throws Throwable;
}
