package royaleserver.network.protocol.client;

import royaleserver.network.protocol.Handler;
import royaleserver.network.protocol.client.messages.*;

public interface ClientMessageHandler extends Handler {
	boolean handleAccountUnlock(AccountUnlock message) throws Throwable;
	boolean handleClanAskData(ClanAskData message) throws Throwable;
	boolean handleClanAskJoinable(ClanAskJoinable message) throws Throwable;
	boolean handleClanChatMessage(ClanChatMessage message) throws Throwable;
	boolean handleClanCreate(ClanCreate message) throws Throwable;
	boolean handleClanJoin(ClanJoin message) throws Throwable;
	boolean handleClanLeave(ClanLeave message) throws Throwable;
	boolean handleClanSearch(ClanSearch message) throws Throwable;
	boolean handleClientCommands(ClientCommands message) throws Throwable;
	boolean handleClientHello(ClientHello message) throws Throwable;
	boolean handleConnectionInfo(ConnectionInfo message) throws Throwable;
	boolean handleHomeAskData(HomeAskData message) throws Throwable;
	boolean handleHomeAskDataOwn(HomeAskDataOwn message) throws Throwable;
	boolean handleInboxAsk(InboxAsk message) throws Throwable;
	boolean handleLogin(Login message) throws Throwable;
	boolean handleMatchmakeCancel(MatchmakeCancel message) throws Throwable;
	boolean handleMatchmakeStart(MatchmakeStart message) throws Throwable;
	boolean handleCancelChallenge(CancelChallenge message) throws Throwable;
	boolean handleNameChange(NameChange message) throws Throwable;
	boolean handleNameCheck(NameCheck message) throws Throwable;
	boolean handlePing(Ping message) throws Throwable;
	boolean handleTournamentAskJoinable(TournamentAskJoinable message) throws Throwable;
}
