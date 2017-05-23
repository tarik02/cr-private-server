package royaleserver.protocol.messages;

import royaleserver.protocol.messages.client.*;

public interface MessageHandler {
	boolean handleClientHello(ClientHello message) throws Throwable;
	boolean handleLogin(Login message) throws Throwable;
	boolean handleClientCapabilities(ClientCapabilities message) throws Throwable;
	boolean handleCancelMatchmake(CancelMatchmake message) throws Throwable;
	boolean handleKeepAlive(KeepAlive message) throws Throwable;
	boolean handleInboxOpened(InboxOpened message) throws Throwable;
	boolean handleEndClientTurn(EndClientTurn message) throws Throwable;
	boolean handleVisitHome(VisitHome message) throws Throwable;
	boolean handleHomeBattleReplay(HomeBattleReplay message) throws Throwable;
	boolean handleAskForAllianceData(AskForAllianceData message) throws Throwable;
	boolean handleAvatarNameCheckRequest(AvatarNameCheckRequest message) throws Throwable;
	boolean handleAskForJoinableAlliancesList(AskForJoinableAlliancesList message) throws Throwable;
	boolean handleAskForJoinableTournaments(AskForJoinableTournaments message) throws Throwable;
	boolean handleJoinAlliance(JoinAlliance message) throws Throwable;
	boolean handleLeaveAlliance(LeaveAlliance message) throws Throwable;
	boolean handleDonateAllianceUnit(DonateAllianceUnit message) throws Throwable;
	boolean handleSearchAlliances(SearchAlliances message) throws Throwable;
	boolean handleAskForAllianceRankingList(AskForAllianceRankingList message) throws Throwable;
	boolean handleAskForTVContent(AskForTVContent message) throws Throwable;
	boolean handleAskForAvatarRankingList(AskForAvatarRankingList message) throws Throwable;
	boolean handleAskForAvatarLocalRanking(AskForAvatarLocalRanking message) throws Throwable;
	boolean handleAskForAvatarStream(AskForAvatarStream message) throws Throwable;
	boolean handleAskForBattleReplayStream(AskForBattleReplayStream message) throws Throwable;
	boolean handleStartMission(StartMission message) throws Throwable;
	boolean handleGoHome(GoHome message) throws Throwable;
	boolean handleChangeAvatarName(ChangeAvatarName message) throws Throwable;
}
