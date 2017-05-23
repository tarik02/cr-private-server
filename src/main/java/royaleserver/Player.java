package royaleserver;

import royaleserver.logic.Arena;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.Session;
import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.client.*;
import royaleserver.protocol.messages.command.BuyChest;
import royaleserver.protocol.messages.command.OpenChest;
import royaleserver.protocol.messages.command.OpenChestOK;
import royaleserver.protocol.messages.command.StartFight;
import royaleserver.protocol.messages.component.AllianceHeaderEntry;
import royaleserver.protocol.messages.component.Card;
import royaleserver.protocol.messages.component.CommandComponent;
import royaleserver.protocol.messages.server.*;
import royaleserver.utils.SCID;

public class Player implements MessageHandler, CommandHandler {
	protected Server server;
	protected Session session;

	protected long accountId;

	public Player(long accountId, Server server, Session session) {
		this.accountId = accountId;
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
		CancelMatchmakeDone response = new CancelMatchmakeDone();
		session.sendMessage(response);

		MatchmakeInfo response_1 = new MatchmakeInfo();
		session.sendMessage(response_1);

		return true;
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
		boolean handled = true;

		for (CommandComponent commandComponent : message.commands) {
			Command command = commandComponent.command;

			if (command == null) {
				handled = false;
				continue;
			}

			handled = handled && command.handle(this);
		}

		return handled;
	}

	@Override
	public boolean handleVisitHome(VisitHome message) throws Throwable {
		VisitedHomeData response = new VisitedHomeData();

		response.homeID = message.accountID;
		response.arena = 8;
		response.trophies = 3500;
		response.level = 13;
		response.username = "Tester";

		session.sendMessage(response);
		return true;
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
		response.alliances[0].Id = 1;
		response.alliances[0].name = "TestClan";
		response.alliances[0].badge = new SCID(16, 150);
		response.alliances[0].type = response.alliances[0].CLAN_OPEN;
		response.alliances[0].numberOfMembers = 1;
		response.alliances[0].score = 10000;
		response.alliances[0].requiredScore = 2000;
		response.alliances[0].unknown_7 = 0;
		response.alliances[0].unknown_8 = 0;
		response.alliances[0].currenRank = 0;
		response.alliances[0].unknown_10 = 0;
		response.alliances[0].donations = 0;
		response.alliances[0].unknown_12 = 0;
		response.alliances[0].unknown_13 = 0;
		response.alliances[0].unknown_14 = 1;
		response.alliances[0].unknown_15 = 12;
		response.alliances[0].region = 57;
		response.alliances[0].unknown_17 = 6;

		session.sendMessage(response);

		return true;
	}

	@Override
	public boolean handleAskForJoinableTournaments(AskForJoinableTournaments message) throws Throwable {
		TournamentListSend response = new TournamentListSend();
		session.sendMessage(response);

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
	public boolean handleStartFight(StartFight command) throws Throwable {
		SectorState response = new SectorState();

		response.homeID = accountId;
		response.isTrainer = 0;
		response.username = "Tester";
		response.wins = 100;
		response.looses = 100;
		response.arena = Arena.by("Arena_T");
		response.trophies = 3500;
		response.gold = 10000;
		response.gems = 10000;
		response.levelExperience = 0;
		response.level = 13;

		session.sendMessage(response);

		return false;
	}

	@Override
	public boolean handleStartMission(StartMission message) throws Throwable {
		SectorState response = new SectorState();

		response.homeID = accountId;
		response.isTrainer = 1;
		response.username = "Tester";
		response.wins = 100;
		response.looses = 100;
		response.arena = Arena.by("Arena_T");
		response.trophies = 3500;
		response.gold = 10000;
		response.gems = 10000;
		response.levelExperience = 0;
		response.level = 13;

		session.sendMessage(response);
		return true;
	}

	@Override
	public boolean handleGoHome(GoHome message) throws Throwable {
		sendOwnHomeData();
		return true;
	}

	@Override
	public boolean handleChangeAvatarName(ChangeAvatarName message) throws Throwable {
		return false;
	}


	@Override
	public boolean handleBuyChestCommand(BuyChest command) throws Throwable {
		AvailableServerCommand response = new AvailableServerCommand();
		response.command.command = new OpenChestOK();
		session.sendMessage(response);

		return true;
	}

	@Override
	public boolean handleOpenChestCommand(OpenChest command) throws Throwable {
		AvailableServerCommand response = new AvailableServerCommand();
		response.command.command = new OpenChestOK();
		session.sendMessage(response);

		return true;
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
		loginFailed.unknown_7 = (byte) 0;
		loginFailed.unknown_8 = "";
		session.sendMessage(loginFailed);
		session.close();
	}

	public void sendOwnHomeData() {
		OwnHomeData ownHomeData = new OwnHomeData();

		ownHomeData.homeId = accountId;
		ownHomeData.arena = Arena.by("Arena_T");
		ownHomeData.lastArena = Arena.by("Arena_T");
		ownHomeData.trophies = 4000;
		ownHomeData.username = "Tester";
		ownHomeData.gold = 10000;
		ownHomeData.gems = 10000;
		ownHomeData.levelExperience = 0;
		ownHomeData.level = 13;
		ownHomeData.lastLevel = 13;

		/*ownHomeData.cards = new Card[80]; // Fill it for testing
		for (int i = 0; i < ownHomeData.cards.length; ++i) {
			//(ownHomeData.cards[i] = new Card()).cardId = i;
		}*/

		session.sendMessage(ownHomeData);
	}
}
