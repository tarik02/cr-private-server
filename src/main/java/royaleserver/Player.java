package royaleserver;

import royaleserver.database.entity.*;
import royaleserver.database.service.ClanService;
import royaleserver.database.service.PlayerService;
import royaleserver.logic.*;
import royaleserver.network.NetworkSession;
import royaleserver.network.protocol.client.messages.Login;
import royaleserver.protocol.messages.Command;
import royaleserver.protocol.messages.CommandHandler;
import royaleserver.protocol.messages.MessageHandler;
import royaleserver.protocol.messages.client.*;
import royaleserver.protocol.messages.command.*;
import royaleserver.protocol.messages.component.*;
import royaleserver.protocol.messages.component.Card;
import royaleserver.protocol.messages.server.*;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Player implements MessageHandler, CommandHandler {
	private static final Logger logger = LogManager.getLogger(Player.class);

	protected Server server;
	protected NetworkSession session;

	protected boolean closed;

	protected PlayerEntity entity;
	protected Random random;

	public Player(PlayerEntity entity, Server server, NetworkSession session) {
		this.entity = entity;
		this.server = server;
		this.session = session;

		this.closed = false;
		server.addPlayer(this);

		random = new Random(entity.getRandomSeed());
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
		response.levelExperience = entity.getExpLevelExperience();

		ExpLevel expLevel = entity.getLogicExpLevel(), lastExpLevel = entity.getLogicLastExpLevel();
		response.level = expLevel.getIndex();
		response.lastLevel = lastExpLevel.getIndex();
		if (expLevel != lastExpLevel) {
			entity.setLogicLastExpLevel(expLevel);
		}

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

	// API

	/**
	 * Adds experience and increases level if needed
	 * @param count of experience to add
	 */
	public void addExperience(int count) {
		int newExp = entity.getExpLevelExperience() + count;
		ExpLevel level = entity.getLogicExpLevel();

		if (newExp >= level.getExpToNextLevel()) {
			ExpLevel nextLevel;

			do {
				nextLevel = ExpLevel.by(level.getName() + 1);
				newExp -= level.getExpToNextLevel();

				if (nextLevel == null) {
					entity.setLogicExpLevel(level);
					entity.setExpLevelExperience(level.getExpToNextLevel());
					return;
				}

				level = nextLevel;
			} while (newExp >= level.getExpToNextLevel());

			entity.setExpLevelExperience(newExp);
			entity.setLogicExpLevel(nextLevel);
		} else {
			entity.setExpLevelExperience(newExp);
		}
	}

	protected void openChest(Chest chest) {
		Rarity common = Rarity.by("Common");
		Rarity rare = Rarity.by("Rare");
		Rarity epic = Rarity.by("Epic");
		Rarity legendary = Rarity.by("Legendary");

		Map<royaleserver.logic.Card, Integer> cards = new HashMap<>();

		float rewardMultiplier = chest.getArena().getChestRewardMultiplier() / 100;
		int spellsCount = (int)(chest.getRandomSpells() * rewardMultiplier);
		int differentSpells = (int)(chest.getDifferentSpells() * rewardMultiplier);

		float minimumRareCount = entity.getRareChance() + spellsCount / chest.getRareChance();
		float minimumEpicCount = entity.getEpicChance() + spellsCount / chest.getEpicChance();
		float minimumLegendaryCount = entity.getLegendaryChance() + spellsCount / chest.getLegendaryChance();

		float rareAdder = random.nextFloat() / 2;
		float epicAdder = random.nextFloat() / 2;
		float legendaryAdder = random.nextFloat() / 2;

		if (minimumLegendaryCount + legendaryAdder > 1) {
			List<royaleserver.logic.Card> candidates = royaleserver.logic.Card.select(legendary, entity.getLogicArena(), random);

			while (minimumLegendaryCount + legendaryAdder > 1) {
				int count = generateCardOfRarity(minimumLegendaryCount, legendaryAdder, cards, candidates);

				--differentSpells;
				spellsCount -= count;
				minimumLegendaryCount -= count;
			}
		}

		if (minimumEpicCount + epicAdder > 1) {
			List<royaleserver.logic.Card> candidates = royaleserver.logic.Card.select(epic, entity.getLogicArena(), random);

			while (minimumEpicCount + epicAdder > 1) {
				int count = generateCardOfRarity(minimumEpicCount, epicAdder, cards, candidates);

				--differentSpells;
				spellsCount -= count;
				minimumEpicCount -= count;
			}
		}

		if (minimumRareCount + rareAdder > 1) {
			List<royaleserver.logic.Card> candidates = royaleserver.logic.Card.select(rare, entity.getLogicArena(), random);

			while (minimumRareCount + rareAdder > 1) {
				int count = generateCardOfRarity(minimumRareCount, rareAdder, cards, candidates);

				--differentSpells;
				spellsCount -= count;
				minimumRareCount -= count;
			}
		}

		List<royaleserver.logic.Card> candidates = royaleserver.logic.Card.select(common, entity.getLogicArena(), random);
		while (spellsCount > 0 && differentSpells > 0) {
			int count = generateCardOfRarity(spellsCount / differentSpells, 0, cards, candidates);

			--differentSpells;
			spellsCount -= count;
		}

		entity.setRareChance(minimumRareCount);
		entity.setEpicChance(minimumEpicCount);
		entity.setLegendaryChance(minimumLegendaryCount);

		int realSpellsCount = (int)(chest.getRandomSpells() * rewardMultiplier) - spellsCount;
		int minGold = chest.getMinGoldPerCard() * realSpellsCount;
		int maxGold = chest.getMaxGoldPerCard() * realSpellsCount;

		OpenChestOK command = new OpenChestOK();
		command.gold = minGold + random.nextInt(maxGold - minGold);
		command.gems = 0;
		command.chestItems = new ChestItem[cards.size()];

		// TODO: Order
		int i = 0;
		for (Map.Entry<royaleserver.logic.Card, Integer> card : cards.entrySet()) {
			ChestItem chestItem = new ChestItem();
			chestItem.card = card.getKey().getIndex();
			chestItem.quantity = card.getValue();

			command.chestItems[i++] = chestItem;
		}

		AvailableServerCommand response = new AvailableServerCommand();
		response.command.command = command;
		session.sendMessage(response);
	}

	protected int generateCardOfRarity(float minimumCount, float adder, Map<royaleserver.logic.Card, Integer> cards, List<royaleserver.logic.Card> candidates) {
		int count = (int)Math.floor(random.nextFloat() * (minimumCount + adder));

		royaleserver.logic.Card card;

		if (candidates.size() > 0) {
			int i = random.nextInt(candidates.size());
			card = candidates.remove(i);
			candidates.remove(card);
			cards.put(card, count);
		} else {
			// TODO: Add to existing
		}

		return count;
	}

	/**
	 * @param nickname to check
	 * @return true if nickname is allowed to use
	 */
	public boolean checkNickname(String nickname) {
		// TODO: More checks
		return nickname.length() > 0 && nickname.length() < 16;
	}

	/**
	 * Saves player entity to database.
	 */
	public void save() {
		PlayerService playerService = server.getDataManager().getPlayerService();
		entity.setRandomSeed(random.nextLong());
		playerService.update(entity);
	}

	public void updateOnline() {
		entity.setOnline(true);
		save();
	}

	/**
	 * Disconnect player showing the reason.
	 *
	 * @param reason message
	 */
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

		server.removePlayer(this);
		entity.setOnline(false);
		save();
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
		}
		session.close();

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
			response.level = responseEntity.getLogicExpLevel().getIndex();
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
		ClanService clanService = server.getDataManager().getClanService();
		ClanEntity clan = clanService.searchById(message.allianceId);

		if (clan != null) {
			AllianceData allianceData = AllianceData.from(clan);
			session.sendMessage(allianceData);
		}

		return true;
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
		final ClanService clanService = server.getDataManager().getClanService();
		final List<ClanEntity> clans = clanService.search(null, 0, 0, 0, true);

		JoinableAllianceList response = new JoinableAllianceList();
		response.alliances = new AllianceHeaderEntry[clans.size()];

		int i = 0;
		for (ClanEntity clan : clans) {
			response.alliances[i] = AllianceHeaderEntry.from(clan);

			++i;
		}

		session.sendMessage(response); // TODO: Change response message

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
		ClanService clanService = server.getDataManager().getClanService();
		ClanEntity clan = clanService.searchById(message.allianceId);

		if (clan != null && entity.getClan() == null && entity.getTrophies() >= clan.getRequiredTrophies()) {
			entity.setClan(clan);
			entity.setLogicClanRole(ClanRole.by("Member"));
			clan.getMembers().add(entity);
			save();

			JoinClan command = JoinClan.from(clan);

			AvailableServerCommand response = new AvailableServerCommand();
			response.command.command = command;

			AllianceOnlineStatusUpdated response_1 = new AllianceOnlineStatusUpdated();
			response_1.membersOnline = 1;
			response_1.unknown_1 = 0;

			session.sendMessage(response);
			session.sendMessage(response_1);
		}

		return true;
	}

	@Override
	public boolean handleLeaveAlliance(LeaveAlliance message) throws Throwable {
		ClanService clanService = server.getDataManager().getClanService();
		ClanEntity clan = entity.getClan();

		if (clan != null) {
			LeaveClanOK command = LeaveClanOK.from(clan);

			CancelChallengeDone response = new CancelChallengeDone();

			AvailableServerCommand response_1 = new AvailableServerCommand();
			response_1.command.command = command;

			clan.getMembers().remove(entity);
			entity.setClan(null);
			entity.setClanRole(null);
			save();

			session.sendMessage(response);
			session.sendMessage(response_1);
		}

		return true;
	}

	@Override
	public boolean handleDonateAllianceUnit(DonateAllianceUnit message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleSearchAlliances(SearchAlliances message) throws Throwable {
		final ClanService clanService = server.getDataManager().getClanService();
		final List<ClanEntity> clans = clanService.search(message.searchString, message.minMembers, message.maxMembers, message.minTrophies, message.findOnlyJoinableClans);

		JoinableAllianceList response = new JoinableAllianceList();
		response.alliances = new AllianceHeaderEntry[clans.size()];

		int i = 0;
		for (ClanEntity clan : clans) {
			response.alliances[i] = AllianceHeaderEntry.from(clan);

			++i;
		}

		session.sendMessage(response); // TODO: Change response message

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
	public boolean handleCreateAlliance(CreateAlliance message) throws Throwable {
		ClanBadge badge = ClanBadge.by(message.badge);
		if (badge == null) {
			logger.warn("Player " + entity.getName() + "#" + entity.getId() + " tried to create clan with unknown badge.");
			return true;
		}

		// TODO: Check and set location and type

		if (entity.getClan() == null && entity.getGold() >= 1000) {
			entity.setGold(entity.getGold() - 1000);

			ClanService clanService = server.getDataManager().getClanService();
			ClanEntity clan = new ClanEntity();
			clan.setName(message.name);
			clan.setDescription(message.description);
			clan.setLogicBadge(badge);
			//clan.setType();
			clan.setRequiredTrophies(message.minTrophies);
			//clan.setLocaltion();
			clan = clanService.add(clan);

			entity.setClan(clan);
			entity.setLogicClanRole(ClanRole.by("Leader"));
			clan.getMembers().add(entity);
			save();

			// Send some information about clan
			JoinClan command = JoinClan.from(clan);

			AvailableServerCommand response = new AvailableServerCommand();
			response.command.command = command;

			AllianceOnlineStatusUpdated response_1 = new AllianceOnlineStatusUpdated();
			response_1.membersOnline = 1;
			response_1.unknown_1 = 0;

			session.sendMessage(response);
			session.sendMessage(response_1);
		}

		return true;
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
		int slot = command.slot;
		System.out.println(slot);
		for (HomeChestEntity homeChest : entity.getHomeChests()) {
			if (homeChest.getSlot() == slot) {
				if (homeChest.getStatus() == HomeChestStatus.OPENED ||
						(homeChest.getStatus() == HomeChestStatus.OPENING &&
								homeChest.getOpenEnd().getTime() < System.currentTimeMillis())) {
					//server.getDataManager().getHomeChestService().delete(homeChest);

					openChest(homeChest.getLogicChest());
				}

				break;
			}
		}

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
		addExperience(1000);

		return false;
	}

	@Override
	public boolean handleClanChatMessage(ClanChatMessage message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleChangeDeckCardCommand(ChangeDeckCard command) throws Throwable {
		return false;
	}
}
