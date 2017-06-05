package royaleserver;

import java.util.*;
import royaleserver.logic.*;
import royaleserver.network.protocol.client.commands.*;
import royaleserver.network.protocol.client.messages.*;
import royaleserver.network.protocol.server.messages.*;

import royaleserver.database.entity.ClanEntity;
import royaleserver.database.entity.HomeChestEntity;
import royaleserver.database.entity.HomeChestStatus;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.database.service.ClanService;
import royaleserver.database.service.PlayerService;
import royaleserver.network.Filler;
import royaleserver.network.NetworkSession;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.server.commands.ChestOpenOk;
import royaleserver.network.protocol.server.commands.ClanJoinOk;
import royaleserver.network.protocol.server.commands.ClanLeaveOk;
import royaleserver.network.protocol.server.commands.NameSet;
import royaleserver.network.protocol.server.components.ChestItem;
import royaleserver.network.protocol.server.components.ClanHeader;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;
import royaleserver.utils.Pair;

public class Player implements ClientMessageHandler, ClientCommandHandler {
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
		HomeDataOwn response = new HomeDataOwn();
		Filler.fill(response, entity);

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
				nextLevel = ExpLevel.by(level.getNumericName() + 1);
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

		Set<Pair<royaleserver.logic.Card, Integer>> cards = new TreeSet<>(
				Comparator.comparingInt(a -> a.first().getRarity().getSortCapacity() * a.second()));

		float rewardMultiplier = chest.getArena().getChestRewardMultiplier() / 100;
		int minimumSpellsCount = (int)(chest.getRandomSpells() * rewardMultiplier);
		int minimumDifferentSpells = (int)(chest.getDifferentSpells() * rewardMultiplier);

		int minimumRare = minimumSpellsCount / chest.getRareChance();
		int minimumEpic = minimumSpellsCount / chest.getEpicChance();
		int minimumLegendary = minimumSpellsCount / chest.getLegendaryChance();

		float rareCount = minimumRare + entity.getRareChance();
		float epicCount = minimumEpic + entity.getEpicChance();
		float legendaryCount = minimumLegendary + entity.getLegendaryChance();
		float commonCount = minimumSpellsCount - minimumRare - minimumEpic - minimumLegendary;

		int differentCommon = countDifferent(commonCount, minimumSpellsCount, minimumDifferentSpells);
		int differentRare = countDifferent(rareCount, minimumSpellsCount, minimumDifferentSpells);
		int differentEpic = countDifferent(epicCount, minimumSpellsCount, minimumDifferentSpells);
		int differentLegendary = countDifferent(legendaryCount, minimumSpellsCount, minimumDifferentSpells);

		int realSpellsCount = (int)(commonCount + rareCount + epicCount + legendaryCount);

		Map<Rarity, List<royaleserver.logic.Card>> candidates = royaleserver.logic.Card.select(entity.getLogicArena());
		commonCount -= generateCards(cards, candidates.get(common), differentCommon, (int)commonCount);
		rareCount -= generateCards(cards, candidates.get(rare), differentRare, (int)rareCount);
		epicCount -= generateCards(cards, candidates.get(epic), differentEpic, (int)epicCount);
		legendaryCount -= generateCards(cards, candidates.get(legendary), differentLegendary, (int)legendaryCount);

		entity.setRareChance(rareCount);
		entity.setEpicChance(epicCount);
		entity.setLegendaryChance(legendaryCount);

		int minGold = chest.getMinGoldPerCard() * realSpellsCount;
		int maxGold = chest.getMaxGoldPerCard() * realSpellsCount;

		ChestOpenOk command = new ChestOpenOk();
		command.gold = minGold + random.nextInt(maxGold - minGold);
		command.gems = 0; // TODO:
		command.chestItems = new ChestItem[cards.size()];

		int i = 0;
		for (Pair<royaleserver.logic.Card, Integer> card : cards) {
			ChestItem chestItem = new ChestItem();
			chestItem.card = card.first().getIndex();
			chestItem.count = card.second();

			command.chestItems[i++] = chestItem;
		}

		CommandResponse response = new CommandResponse();
		response.command = command;
		session.sendMessage(response);
	}

	private int generateCards(Set<Pair<royaleserver.logic.Card, Integer>> cards, List<royaleserver.logic.Card> candidates,
	                          int different, int count) {
		int first;
		if (different == 1) {
			first = count;
		} else {
			first = (int)(count / different * (0.1f + random.nextFloat()));
		}

		int current = first;
		int sum = 0;

		while (sum < count && different != 1 && sum + current <= count) {
			sum += current;

			addCard(cards, candidates, current);

			current *= 1f + random.nextFloat();
			--different;
		}

		if (sum < count) {
			addCard(cards, candidates, count - sum);
		}

		return count;
	}

	private void addCard(Set<Pair<royaleserver.logic.Card, Integer>> cards, List<royaleserver.logic.Card> candidates, int count) {
		if (candidates.size() != 0) {
			int index = random.nextInt(candidates.size());
			cards.add(new Pair<>(candidates.remove(index), count));
		}
	}

	protected int countDifferent(float rarityCount, int count, int different) {
		return (int)Math.ceil(rarityCount / ((float)count) * different);
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

	// Messages

	@Override
	public boolean handleClanAskData(ClanAskData message) throws Throwable {
		final ClanService clanService = server.getDataManager().getClanService();
		final ClanEntity clan = clanService.searchById(message.clanId);

		if (clan != null) {
			final ClanData response = new ClanData();
			Filler.fill(response, clan);
			session.sendMessage(response);
		}

		return true;
	}

	@Override
	public boolean handleClanAskJoinable(ClanAskJoinable message) throws Throwable {
		final ClanService clanService = server.getDataManager().getClanService();
		final List<ClanEntity> clans = clanService.search(null, 0, 0, 0, true);

		ClanJoinableResponse response = new ClanJoinableResponse();
		response.clans = new ClanHeader[clans.size()];

		int i = 0;
		for (ClanEntity clan : clans) {
			ClanHeader header = new ClanHeader();
			Filler.fill(header, clan);
			response.clans[i++] = header;
		}

		session.sendMessage(response);

		return true;
	}

	@Override
	public boolean handleClanChatMessage(ClanChatMessage message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleClanCreate(ClanCreate message) throws Throwable {
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
			ClanJoinOk command = new ClanJoinOk();
			Filler.fill(command, clan);

			CommandResponse response = new CommandResponse();
			response.command = command;

			/*AllianceOnlineStatusUpdated response_1 = new AllianceOnlineStatusUpdated();
			response_1.membersOnline = 1;
			response_1.unknown_1 = 0;*/

			session.sendMessage(response);
			//session.sendMessage(response_1);
		}

		return true;
	}

	@Override
	public boolean handleClanJoin(ClanJoin message) throws Throwable {
		final ClanService clanService = server.getDataManager().getClanService();
		final ClanEntity clan = clanService.searchById(message.clanId);

		if (clan != null && entity.getClan() == null && entity.getTrophies() >= clan.getRequiredTrophies()) {
			entity.setClan(clan);
			entity.setLogicClanRole(ClanRole.by("Member"));
			clan.getMembers().add(entity);
			save();

			ClanJoinOk command = new ClanJoinOk();
			Filler.fill(command, clan);

			CommandResponse response = new CommandResponse();
			response.command = command;

			/*AllianceOnlineStatusUpdated response_1 = new AllianceOnlineStatusUpdated();
			response_1.membersOnline = 1;
			response_1.unknown_1 = 0;*/

			session.sendMessage(response);
		}

		return true;
	}

	@Override
	public boolean handleClanLeave(ClanLeave message) throws Throwable {
		final ClanService clanService = server.getDataManager().getClanService();
		final ClanEntity clan = entity.getClan();

		if (clan != null) {
			ClanLeaveOk command = new ClanLeaveOk();
			Filler.fill(command, clan);

			CommandResponse response = new CommandResponse();
			response.command = command;

			Set<PlayerEntity> members = clan.getMembers();
			members.remove(entity);
			if (members.size() == 0) {
				clanService.remove(clan);
			} else if (entity.getLogicClanRole() == ClanRole.by("Leader")) {
				int memberIndex = members.size();
				Iterator<PlayerEntity> iterator = members.iterator();

				for (int i = 0; i < memberIndex; ++i) {
					iterator.next();
				}

				PlayerEntity newLeader = iterator.next();
				newLeader.setLogicClanRole(ClanRole.by("Leader"));
			}

			entity.setClan(null);
			entity.setClanRole(null);
			save();

			session.sendMessage(response);
		}

		return true;
	}

	@Override
	public boolean handleClanSearch(ClanSearch message) throws Throwable {
		final ClanService clanService = server.getDataManager().getClanService();
		final List<ClanEntity> clans = clanService.search(message.searchString, message.minMembers, message.maxMembers, message.minTrophies, message.findOnlyJoinableClans);

		ClanJoinableResponse response = new ClanJoinableResponse();
		response.clans = new ClanHeader[clans.size()];

		int i = 0;
		for (ClanEntity clanEntity : clans) {
			ClanHeader clan = new ClanHeader();
			Filler.fill(clan, clanEntity);
			response.clans[i++] = clan;
		}

		session.sendMessage(response); // TODO: Change response message

		return true;
	}

	@Override
	public boolean handleClientCommands(ClientCommands message) throws Throwable {
		boolean handled = true;

		for (ClientCommand command : message.commands) {
			if (command != null) {
				if (!command.handle(this)) {
					handled = false;
					break;
				}
			}
		}

		return handled;
	}

	@Override
	public boolean handleClientHello(ClientHello message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleConnectionInfo(ConnectionInfo message) throws Throwable {
		return true;
	}

	@Override
	public boolean handleHomeAskData(HomeAskData message) throws Throwable {
		PlayerEntity responseEntity;
		boolean isMyProfile;

		if (message.accountId == entity.getId()) {
			responseEntity = entity;
			isMyProfile = true;
		} else {
			PlayerService playerService = server.getDataManager().getPlayerService();
			responseEntity = playerService.get(message.accountId);
			isMyProfile = false;
		}

		if (responseEntity != null) {
			HomeDataVisited response = new HomeDataVisited();
			Filler.fill(response, responseEntity, isMyProfile);
			session.sendMessage(response);
		}

		return true;
	}

	@Override
	public boolean handleHomeAskDataOwn(HomeAskDataOwn message) throws Throwable {
		sendOwnHomeData();
		return true;
	}

	@Override
	public boolean handleInboxAsk(InboxAsk message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleLogin(Login message) throws Throwable {
		return false;
	}

	@Override
	public boolean handleMatchmakeCancel(MatchmakeCancel message) throws Throwable {
		MatchmakeCancelOk response = new MatchmakeCancelOk();
		MatchmakeInfo response2 = new MatchmakeInfo();

		session.sendMessage(response);
		session.sendMessage(response2);

		return true;
	}

	@Override
	public boolean handleMatchmakeStart(MatchmakeStart message) throws Throwable {
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
	public boolean handleNameChange(NameChange message) throws Throwable {
		NameSet command = new NameSet();

		if (checkNickname(message.name)) {
			command.name = message.name;
			entity.setName(message.name);
		} else {
			command.name = entity.getName();
		}

		CommandResponse response = new CommandResponse();
		response.command = command;
		session.sendMessage(response);

		return true;
	}

	@Override
	public boolean handleNameCheck(NameCheck message) throws Throwable {
		NameCheckOk response = new NameCheckOk();
		if (checkNickname(message.name)) {
			response.name = message.name;
		} else {
			// TODO: Failed message
			response.name = entity.getName(); // Return old nickname back
		}
		session.sendMessage(response);
		return true;
	}

	@Override
	public boolean handlePing(Ping message) throws Throwable {
		session.sendMessage(new Pong());
		return true;
	}

	@Override
	public boolean handleTournamentAskJoinable(TournamentAskJoinable message) throws Throwable {
		return false;
	}

	// Commands

	@Override
	public boolean handleChallengeBuy(ChallengeBuy command) throws Throwable {
		return false;
	}

	@Override
	public boolean handleChestBuy(ChestBuy command) throws Throwable {
		// TODO: Check chest, gems, etc.
		openChest(command.chest);
		return true;
	}

	@Override
	public boolean handleChestCardNext(ChestCardNext command) throws Throwable {
		// TODO: Draft chests
		return true;
	}

	@Override
	public boolean handleChestOpen(ChestOpen command) throws Throwable {
		int slot = command.slot;

		for (HomeChestEntity homeChest : entity.getHomeChests()) {
			if (homeChest.getSlot() == slot) {
				if (homeChest.getStatus() == HomeChestStatus.OPENED ||
						(homeChest.getStatus() == HomeChestStatus.OPENING &&
								homeChest.getOpenEnd().getTime() < System.currentTimeMillis())) {
					server.getDataManager().getHomeChestService().delete(homeChest);

					openChest(homeChest.getLogicChest());
				}

				break;
			}
		}

		return true;
	}

	public boolean handleDeckChangeCard(DeckChangeCard command) throws Throwable {
		return false;
	}

	@Override
	public boolean handleFightStart(FightStart command) throws Throwable {
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

		return true;
	}
}
