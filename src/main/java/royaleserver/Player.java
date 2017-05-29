package royaleserver;

import royaleserver.database.entity.HomeChestEntity;
import royaleserver.database.entity.HomeChestStatus;
import royaleserver.database.entity.PlayerCardEntity;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.database.service.PlayerService;
import royaleserver.logic.Arena;
import royaleserver.protocol.Session;
import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.messages.client.*;
import royaleserver.protocol.messages.command.*;
import royaleserver.protocol.messages.component.*;
import royaleserver.protocol.messages.server.*;
import royaleserver.utils.SCID;

public class Player implements MessageHandler, CommandHandler {
	protected Server server;
	protected Session session;

	protected boolean closed;

	protected PlayerEntity entity;

	public Player(PlayerEntity entity, Server server, Session session) {
		this.entity = entity;
		this.server = server;
		this.session = session;

		this.closed = false;
	}

	/**
	 * @apiNote For internal usage only
	 */
	public void sendOwnHomeData() {
		OwnHomeData response = new OwnHomeData();

		Arena arena = entity.getLogicArena();
		response.homeId = entity.getId();
		response.arena = arena;
		response.lastArena = arena;
		response.trophies = entity.getTrophies();
		response.username = entity.getName();
		response.gold = entity.getGold();
		response.gems = entity.getGems();
		response.levelExperience = 0;
		response.level = 13;
		response.lastLevel = 13;

		response.homeChests = new HomeChest[entity.getHomeChests().size()];
		int i = 0;
		for (HomeChestEntity homeChestEntity : entity.getHomeChests()) {
			HomeChest homeChest = response.homeChests[i++] = new HomeChest();
			homeChest.slot = homeChestEntity.getSlot();
			homeChest.chest = homeChestEntity.getLogicChest();
			switch (homeChestEntity.getStatus()) {
			case IDLE:
				homeChest.status = HomeChest.STATUS_STATIC;
				break;
			case OPENING:
				if (homeChestEntity.getOpenEnd().getTime() < System.currentTimeMillis()) {
					homeChestEntity.setStatus(HomeChestStatus.OPENED);
					server.getDataManager().getHomeChestService().put(homeChestEntity);
				} else {
					homeChest.status = HomeChest.STATUS_OPENING;
					homeChest.ticksToOpen = (int)((homeChestEntity.getOpenEnd().getTime() - System.currentTimeMillis()) / 50); // Convert millis to ticks
					break;
				}
			case OPENED:
				homeChest.status = HomeChest.STATUS_OPENED;
				break;
			}
		}

		response.cards = new Card[entity.getCards().size()];
		i = 0;
		for (PlayerCardEntity cardEntity : entity.getCards()) {
			Card card = response.cards[i++] = new Card();
			card.card = cardEntity.getLogicCard();
			card.level = cardEntity.getLevel();
			card.count = cardEntity.getCount();
		}

		response.clan = PlayerClan.from(entity);

		session.sendMessage(response);
	}

	/**
	 * @param nickname to check
	 * @return true if nickname is allowed to use
	 */
	public boolean checkNickname(String nickname) {
		// TODO: More checks
		return nickname.length() > 0 && nickname.length() < 16;
	}

	public void disconnect(String reason) {
		close(reason, true);
	}

	/**
	 * @return true if closing is success
	 * @apiNote For internal usage only
	 */
	public boolean close() {
		return close(null, true);
	}

	/**
	 * @param reason Reason of closing
	 * @return true if closing is success
	 * @apiNote For internal usage only
	 */
	public boolean close(final String reason) {
		return close(reason, true);
	}

	/**
	 * @param reason         Reason of closing
	 * @param sendDisconnect If true, then server will send LoginFailed packet
	 * @return true if closing is success
	 * @apiNote For internal usage only
	 */
	public boolean close(String reason, final boolean sendDisconnect) {
		if (closed) {
			return false;
		}
		closed = true;

		PlayerService playerService = server.getDataManager().getPlayerService();
		playerService.update(entity);
		entity = null;

		if (reason == null) {
			reason = "";
		}

		if (sendDisconnect) {
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

		return true;
	}


	// Handlers

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
		PlayerEntity responseEntity;

		if (message.accountID == entity.getId()) {
			responseEntity = entity;
			response.isMyProfile = true;
		} else {
			PlayerService playerService = server.getDataManager().getPlayerService();
			responseEntity = playerService.get(message.accountID);
			response.isMyProfile = false;
		}

		if (responseEntity != null) {
			Arena arena = responseEntity.getLogicArena();
			response.homeID = responseEntity.getId();
			response.arena = arena;
			response.trophies = responseEntity.getTrophies();
			response.level = 13;
			response.username = responseEntity.getName();

			response.clan = PlayerClan.from(responseEntity);
		}

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
		AvatarNameCheckResponse response = new AvatarNameCheckResponse();
		if (checkNickname(message.username)) {
			response.username = message.username;
		} else {
			response.username = entity.getName(); // Return old nickname back
		}
		session.sendMessage(response);

		return true;
	}

	@Override
	public boolean handleSetNickname(SetNickname command) throws Throwable {
		if (checkNickname(command.nickname)) {
			entity.setName(command.nickname);
		}

		return true;
	}

	@Override
	public boolean handleChangeAvatarName(ChangeAvatarName message) throws Throwable {
		SetNickname command = new SetNickname();

		if (checkNickname(message.username)) {
			command.nickname = message.username;
		} else {
			command.nickname = entity.getName();
		}

		AvailableServerCommand response = new AvailableServerCommand();
		response.command.command = command;
		session.sendMessage(response);

		return true;
	}

	@Override
	public boolean handleAskForJoinableAlliancesList(AskForJoinableAlliancesList message) throws Throwable {
		JoinableAllianceList response = new JoinableAllianceList();

		response.alliances = new AllianceHeaderEntry[1];
		response.alliances[0] = new AllianceHeaderEntry();
		response.alliances[0].id = 1;
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

		return true;
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
		/*final ClanService clanService = server.getDataManager().getClanService();
		final List<ClanEntity> clans = clanService.search(message.searchString, message.minMembers, message.maxMembers, message.minTrophies, message.findOnlyJoinableClans);

		JoinableAllianceList response = new JoinableAllianceList();
		response.alliances = new AllianceHeaderEntry[clans.size()];

		int i = 0;
		for (ClanEntity clan : clans) {
			response.alliances[i] = AllianceHeaderEntry.from(clan);

			++i;
		}

		session.sendMessage(response);*/

		return true;
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

		response.homeID = entity.getId();
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

		response.homeID = entity.getId();
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
}
