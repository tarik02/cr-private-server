package royaleserver;

import royaleserver.protocol.Handler;
import royaleserver.protocol.Session;
import royaleserver.protocol.messages.client.*;
import royaleserver.protocol.messages.component.AllianceHeaderEntry;
import royaleserver.protocol.messages.server.AvatarStream;
import royaleserver.protocol.messages.server.JoinableAllianceList;
import royaleserver.protocol.messages.server.KeepAliveOk;
import royaleserver.protocol.messages.server.LoginFailed;

public class Player implements Handler {
	protected Server server;
	protected Session session;

	public Player(Server server, Session session) {
		this.server = server;
		this.session = session;
	}

	public void close() {

	}

	@Override
	public boolean handleClientHello(ClientHello message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleLogin(Login message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleClientCapabilities(ClientCapabilities message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleCancelMatchmake(CancelMatchmake message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleKeepAlive(KeepAlive message) throws Throwable {
		KeepAliveOk response = new KeepAliveOk();
		session.sendMessage(response);
		return true;
	}

	@Override
	public boolean handleInboxOpened(InboxOpened message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleEndClientTurn(EndClientTurn message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleVisitHome(VisitHome message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleHomeBattleReplay(HomeBattleReplay message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleAskForAllianceData(AskForAllianceData message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleAvatarNameCheckRequest(AvatarNameCheckRequest message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleAskForJoinableAlliancesList(AskForJoinableAlliancesList message) throws Throwable {

		JoinableAllianceList response = new JoinableAllianceList();

		response.alliances = new AllianceHeaderEntry[1];
		response.alliances[0] = new AllianceHeaderEntry();
		response.alliances[0].Id = 0;
		response.alliances[0].name = "Hello";

		//session.sendMessage(response);

		return false;
	}

	@Override
	public boolean handleAskForJoinableTournaments(AskForJoinableTournaments message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleJoinAlliance(JoinAlliance message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleLeaveAlliance(LeaveAlliance message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleDonateAllianceUnit(DonateAllianceUnit message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleSearchAlliances(SearchAlliances message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleAskForAllianceRankingList(AskForAllianceRankingList message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleAskForTVContent(AskForTVContent message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleAskForAvatarRankingList(AskForAvatarRankingList message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleAskForAvatarLocalRanking(AskForAvatarLocalRanking message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleAskForAvatarStream(AskForAvatarStream message) throws Throwable {
		AvatarStream avatarStream = new AvatarStream();
		avatarStream.unknown_0 = 0;
		session.sendMessage(avatarStream);
		return true;
	}

	@Override
	public boolean handleAskForBattleReplayStream(AskForBattleReplayStream message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleStartMission(StartMission message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleGoHome(GoHome message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleChangeAvatarName(ChangeAvatarName message) throws Throwable {
		return false;
	}

	public void disconnect(String reason) {
		LoginFailed loginFailed = new LoginFailed();
		loginFailed.errorCode = 7;
		loginFailed.resourceFingerprintData = server.getResourceFingerprint();
		loginFailed.redirectDomain = "";
		loginFailed.contentURL = "http://7166046b142482e67b30-2a63f4436c967aa7d355061bd0d924a1.r65.cf1.rackcdn.com";
		loginFailed.updateURL = "";
		loginFailed.reason = reason;
		loginFailed.secondsUntilMaintenanceEnd = 0;
		loginFailed.unknown_7 = (byte)0;
		loginFailed.unknown_8 = "";
		session.sendMessage(loginFailed);
		session.close();
	}
}
