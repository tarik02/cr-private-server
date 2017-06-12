package royaleserver;

import royaleserver.database.entity.*;
import royaleserver.database.service.ClanService;
import royaleserver.database.service.PlayerCardService;
import royaleserver.database.service.PlayerService;
import royaleserver.logic.*;
import royaleserver.network.Filler;
import royaleserver.network.NetworkSession;
import royaleserver.network.NetworkSessionHandler;
import royaleserver.network.protocol.client.ClientCommand;
import royaleserver.network.protocol.client.ClientCommandHandler;
import royaleserver.network.protocol.client.ClientMessageHandler;
import royaleserver.network.protocol.client.commands.*;
import royaleserver.network.protocol.client.messages.*;
import royaleserver.network.protocol.server.commands.ChestOpenOk;
import royaleserver.network.protocol.server.commands.ClanJoinOk;
import royaleserver.network.protocol.server.commands.ClanLeaveOk;
import royaleserver.network.protocol.server.commands.NameSet;
import royaleserver.network.protocol.server.components.ClanHeader;
import royaleserver.network.protocol.server.messages.*;
import royaleserver.utils.DataStream;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.util.*;

public class Player extends NetworkSession implements ClientMessageHandler, ClientCommandHandler {
	private static final Logger logger = LogManager.getLogger(Player.class);

	protected PlayerEntity entity;
	protected Random random;

	protected OpeningChest openingChest = null;
	protected final Map<Card, PlayerCard> cards = new HashMap<>();
	protected final Set<PlayerCard> cardsToAdd = new HashSet<>();
	protected final Set<PlayerCard> cardsToUpdate = new HashSet<>();

	protected Deck deck;
	protected ArrayList<Deck> decks = new ArrayList<>();
	protected ArrayList<PlayerCard> cardsAfterDeck = new ArrayList<>();

	public Player(PlayerEntity entity, Server server, NetworkSessionHandler session) {
		super(server, session);

		this.entity = entity;
		server.addPlayer(this);

		random = new Random(entity.getRandomSeed());


		LoginOk loginOk = new LoginOk();
		loginOk.userId = loginOk.homeId = entity.getId();
		loginOk.userToken = entity.getPassToken();
		loginOk.gameCenterId = "";
		loginOk.facebookId = "";
		loginOk.serverMajorVersion = 3; // TODO: Make it constant
		loginOk.serverBuild = 193; // TODO: Make it constant
		loginOk.contentVersion = 8; // TODO: Make it constant
		loginOk.environment = "prod";
		loginOk.sessionCount = 5;
		loginOk.playTimeSeconds = 114; // TODO: Get it from store
		loginOk.daysSinceStartedPlaying = 0; // TODO: Get it from store
		loginOk.facebookAppId = "1475268786112433";
		loginOk.serverTime = String.valueOf(System.currentTimeMillis());
		loginOk.accountCreatedDate = String.valueOf(entity.getRegisteredDate().getTime()); // TODO: Get it from store
		loginOk.unknown_16 = 0;
		loginOk.googleServiceId = "";
		loginOk.unknown_18 = "";
		loginOk.unknown_19 = "";
		loginOk.region = "UA"; // TODO: Make it from config
		loginOk.contentURL = "http://7166046b142482e67b30-2a63f4436c967aa7d355061bd0d924a1.r65.cf1.rackcdn.com"; // TODO: Make it from config
		loginOk.eventAssetsURL = "https://event-assets.clashroyale.com"; // TODO: Make it from config
		loginOk.unknown_23 = 1;
		session.sendMessage(loginOk);



		Set<PlayerCardEntity> cardEntities = entity.getCards();
		for (PlayerCardEntity cardEntity : cardEntities) {
			Card card = cardEntity.getLogicCard();
			int level = cardEntity.getLevel(),
				count = cardEntity.getCount();

			PlayerCard playerCard = new PlayerCard(card, level, count, cardEntity);
			cards.put(card, playerCard);
		}

		for (int i = 0; i < getDecksCount(); ++i) {
			decks.add(new Deck());
		}

		Set<PlayerDeckCardEntity> decksCards = entity.getDecksCards();
		for (PlayerDeckCardEntity playerDeckCard : decksCards) {
			int deckSlot = playerDeckCard.getDeckSlot();
			int cardSlot = playerDeckCard.getCardSlot();
			Card card = playerDeckCard.getLogicCard();

			if (deckSlot < decks.size()) {
				Deck deck = decks.get(deckSlot);
				deck.swapCard(cardSlot, cards.get(card));
				deck.setEntity(cardSlot, playerDeckCard);
			}
		}

		// Fill deck with cards, if they aren't present there
		for (Deck deck : decks) {
			deck.markUnchanged();

			for (int i = 0; i < Deck.DECK_CARDS_COUNT; ++i) {
				if (deck.getCard(i) == null) {
					for (PlayerCard card : cards.values()) {
						if (!deck.hasCard(card)) {
							deck.swapCard(i, card);
						}
					}
				}
			}
		}

		changeDeck(entity.getCurrentDeckSlot());

		sendOwnHomeData();
	}

	/**
	 * @apiNote For internal usage only
	 */
	public void sendOwnHomeData() {
		HomeDataOwn response = new HomeDataOwn();
		Filler.fill(response, entity, deck, cardsAfterDeck, decks);

		session.sendMessage(response);
	}

	// API

	/**
	 * Adds experience and increases level if needed
	 *
	 * @param count of experience to merge
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

	/**
	 * Add count cards of given type. If needed, convert cards to gold.
	 *
	 * @param card Card type to add
	 * @param count Count oof cards to add
	 */
	public void addCard(Card card, int count) {
		boolean found = false;
		for (PlayerCard playerCard : this.cards.values()) {
			if (card == playerCard.getCard()) {
				playerCard.addCount(count);
				this.cardsToUpdate.add(playerCard);
				found = true;
				break;
			}
		}

		if (!found) {
			PlayerCard playerCard = new PlayerCard(card, 1, count);
			playerCard.addCount(count);

			// TODO: Convert cards to gold if needed

			this.cardsAfterDeck.add(playerCard);
			this.cards.put(card, playerCard);
			this.cardsToAdd.add(playerCard);
		}
	}

	private void endOpeningChest() {
		if (openingChest != null) {
			openingChest.end();

			List<OpeningChest.CardStack> cards = openingChest.selectedCards();

			for (OpeningChest.CardStack cardStack : cards) {
				addCard(cardStack.card, cardStack.count);
			}

			openingChest = null;
		}
	}

	private void openChest(Chest chest) {
		endOpeningChest();

		ChestOpenOk command = new ChestOpenOk();
		Filler.fill(command, openingChest = generateChest(chest));

		CommandResponse response = new CommandResponse();
		response.command = command;
		session.sendMessage(response);

		if (openingChest.optionSize() == 1) {
			endOpeningChest();
		}
	}

	// TODO: Move it to another file
	protected OpeningChest generateChest(Chest chest) {
		boolean isDraft = chest.isDraftChest();
		OpeningChest.Builder builder = OpeningChest.builder(isDraft);

		final Rarity common = Rarity.by("Common");
		final Rarity rare = Rarity.by("Rare");
		final Rarity epic = Rarity.by("Epic");
		final Rarity legendary = Rarity.by("Legendary");

		float rewardMultiplier = chest.getArena().getChestRewardMultiplier() / 100;
		int minimumSpellsCount = (int)(chest.getRandomSpells() * rewardMultiplier);
		int minimumDifferentSpells = (int)(chest.getDifferentSpells() * rewardMultiplier);

		float minimumRare = (float)minimumSpellsCount / (float)chest.getRareChance();
		float minimumEpic = (float)minimumSpellsCount / (float)chest.getEpicChance();
		float minimumLegendary = (float)minimumSpellsCount / (float)chest.getLegendaryChance();

		float rareCount = minimumRare + entity.getRareChance();
		float epicCount = minimumEpic + entity.getEpicChance();
		float legendaryCount = minimumLegendary + entity.getLegendaryChance();
		float commonCount = minimumSpellsCount - rareCount - epicCount - legendaryCount;

		int differentRare = countDifferent(rareCount, minimumSpellsCount, minimumDifferentSpells);
		int differentEpic = countDifferent(epicCount, minimumSpellsCount, minimumDifferentSpells);
		int differentLegendary = countDifferent(legendaryCount, minimumSpellsCount, minimumDifferentSpells);
		int differentCommon = minimumDifferentSpells - differentRare - differentEpic - differentLegendary;

		int realSpellsCount = (int)(commonCount + rareCount + epicCount + legendaryCount);

		Map<Rarity, List<royaleserver.logic.Card>> candidates = royaleserver.logic.Card.select(entity.getLogicArena());
		commonCount -= generateCards(builder, candidates.get(common), differentCommon, (int)commonCount);
		rareCount -= generateCards(builder, candidates.get(rare), differentRare, (int)rareCount);
		epicCount -= generateCards(builder, candidates.get(epic), differentEpic, (int)epicCount);
		legendaryCount -= generateCards(builder, candidates.get(legendary), differentLegendary, (int)legendaryCount);

		entity.setRareChance(rareCount);
		entity.setEpicChance(epicCount);
		entity.setLegendaryChance(legendaryCount);

		int minGold = chest.getMinGoldPerCard() * realSpellsCount;
		int maxGold = chest.getMaxGoldPerCard() * realSpellsCount;

		builder.gold(minGold + random.nextInt(maxGold - minGold));
		builder.gems(0); // TODO:

		return builder.build();
	}

	private int generateCards(OpeningChest.Builder builder, List<royaleserver.logic.Card> candidates,
	                          int different, int count) {
		int first;
		if (different == 1) {
			first = count;
		} else {
			first = (int)Math.ceil(count / different * (1f + random.nextFloat()));
		}

		int current = first;
		int sum = 0;

		while (different != 1 && sum + current <= count && candidates.size() > builder.optionSize() * 2) {
			if (addStack(builder, candidates, current)) {
				sum += current;
				current *= 1f + random.nextFloat();
				--different;
			}
		}

		if (sum < count) {
			current = count - sum;
			if (addStack(builder, candidates, current)) {
				sum += current;
				current *= 1f + random.nextFloat();
				--different;
			}
		}

		return sum;
	}

	private boolean addStack(OpeningChest.Builder builder, List<royaleserver.logic.Card> candidates, int count) {
		if (candidates.size() > builder.optionSize()) {
			OpeningChest.CardStack[] stack = new OpeningChest.CardStack[builder.optionSize()];
			for (int i = 0; i < stack.length; ++i) {
				int index = random.nextInt(candidates.size());
				Card candidate = candidates.remove(index);

				stack[i] = new OpeningChest.CardStack(candidate, count);
			}
			builder.add(stack);

			return true;
		}

		return false;
	}

	protected int countDifferent(float rarityCount, int count, int different) {
		return (int)Math.ceil((float)different * (rarityCount / (float)count));
	}

	protected void changeDeck(int slot) {
		if (slot < 0 || slot >= decks.size()) {
			throw new IllegalArgumentException("slot");
		}

		Deck newDeck = decks.get(slot);
		if (deck != newDeck) {
			deck = newDeck;

			cardsAfterDeck.clear();
			for (PlayerCard card : cards.values()) {
				if (!deck.hasCard(card)) {
					cardsAfterDeck.add(card);
				}
			}

			entity.setCurrentDeckSlot(slot);
		}
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
	 * @return Maximal count of paralell deck.
	 */
	public int getDecksCount() {
		// TODO: 5 after update
		return 3;
	}

	/**
	 * Saves player entity to database.
	 */
	public void save() {
		PlayerService playerService = server.getDataManager().getPlayerService();
		entity.setRandomSeed(random.nextLong());

		if (cardsToAdd.size() > 0 || cardsToUpdate.size() > 0) {
			PlayerCardService playerCardService = server.getDataManager().getPlayerCardService();
			ArrayList<PlayerCardEntity> addEntities = new ArrayList<>(cardsToAdd.size());
			ArrayList<PlayerCardEntity> updateEntities = new ArrayList<>(cardsToUpdate.size());

			for (PlayerCard card : cardsToUpdate) {
				PlayerCardEntity cardEntity = card.getEntity();
				if (cardEntity != null) {
					cardEntity.setLevel(card.getLevel());
					cardEntity.setCount(card.getCount());
					updateEntities.add(cardEntity);
				}
			}

			for (PlayerCard card : cardsToAdd) {
				PlayerCardEntity cardEntity = new PlayerCardEntity(entity, card.getCard().getDbEntity(), card.getLevel(), card.getCount());
				card.setEntity(cardEntity);
				addEntities.add(cardEntity);
			}

			playerCardService.merge(addEntities, updateEntities);
			cardsToAdd.clear();
			cardsToUpdate.clear();
		}

		ArrayList<PlayerDeckCardEntity> deckCardsAdd = new ArrayList<>();
		ArrayList<PlayerDeckCardEntity> deckCardsUpdate = new ArrayList<>();
		int deckSlot = 0;
		for (Deck deck : decks) {
			if (deck.markUnchanged()) {
				for (int i = 0; i < Deck.DECK_CARDS_COUNT; ++i) {
					PlayerCard card = deck.getCard(i);
					PlayerDeckCardEntity cardEntity = deck.getEntity(i);

					if (card != null) {
						if (cardEntity == null) {
							cardEntity = new PlayerDeckCardEntity(entity, deckSlot, i, card.getCard().getDbEntity());
							deck.setEntity(i, cardEntity);
							deckCardsAdd.add(cardEntity);
						} else {
							cardEntity.setLogicCard(card.getCard());
							deckCardsUpdate.add(cardEntity);
						}
					}
				}
			}

			++deckSlot;
		}
		if (deckCardsAdd.size() != 0 || deckCardsUpdate.size() != 0) {
			if (deckCardsAdd.size() == 0) {
				server.getDataManager().getPlayerDeckCardService().update(deckCardsUpdate);
			} else if (deckCardsUpdate.size() == 0) {
				server.getDataManager().getPlayerDeckCardService().add(deckCardsAdd);
			} else {
				server.getDataManager().getPlayerDeckCardService().merge(deckCardsAdd, deckCardsUpdate);
			}

			deckCardsAdd.clear();
			deckCardsUpdate.clear();
		}

		playerService.update(entity);
	}

	public void updateOnline() {
		entity.setOnline(true);
		save();
	}

	public boolean close(String reason, final boolean sendDisconnect) {
		if (super.close(reason, sendDisconnect)) {
			endOpeningChest();

			server.removePlayer(this);
			entity.setOnline(false);
			save();
			entity = null;

			return true;
		}

		return false;
	}

	public String getReadableIdentifier() {
		return entity.getName() + "#" + entity.getId();
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
			save();
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
	public boolean handleCardUpgrade(CardUpgrade command) throws Throwable {
		for (PlayerCard playerCard : this.cards.values()) {
			PlayerCardEntity cardEntity = playerCard.getEntity();
			Card card = cardEntity.getLogicCard();

			if (card.getScid().getValue() == command.cardSCID.getValue()) {
				int level = cardEntity.getLevel();
				Rarity rarity = card.getRarity();
				int needToUpdateGold = rarity.getUpgradeCost()[level - 1];
				int needToUpdateTroops = rarity.getUpgradeMaterialCount()[level - 1];
				int addExperience = rarity.getUpgradeExp()[level - 1];

				if (entity.getGold() >= needToUpdateGold && cardEntity.getCount() >= needToUpdateTroops) {
					entity.setGold(entity.getGold() - needToUpdateGold);
					addExperience(addExperience);

					playerCard.setLevel(++level);
					playerCard.setCount(cardEntity.getCount() - needToUpdateTroops);

					this.cardsToUpdate.add(playerCard);
				}
			}
		}

		return true;
	}

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
		return true;
	}

	@Override
	public boolean handleChestDraftCardSelect(ChestDraftCardSelect command) throws Throwable {
		if (openingChest != null) {
			openingChest.next(command.selection);
			if (!openingChest.hasCards()) {
				endOpeningChest();
			}
		}

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

	@Override
	public boolean handleChestSeasonRewardOpen(ChestSeasonRewardOpen command) throws Throwable {
		openChest(Chest.by("Draft_Arena_T"));
		return true;
	}

	@Override
	public boolean handleDeckChange(DeckChange command) throws Throwable {
		changeDeck(command.slot);

		return true;
	}

	@Override
	public boolean handleDeckChangeCard(DeckChangeCard command) throws Throwable {
		if (command.slot < 0 || command.slot >= 8) {
			return true;
		}

		if (command.cardIndex != DataStream.RRSINT_NULL) {
			// Swap deck card and other card

			cardsAfterDeck.set(command.cardIndex, deck.swapCard(command.slot, cardsAfterDeck.get(command.cardIndex)));
		} else if (command.slot2 != DataStream.RRSINT_NULL) {
			// Swap two deck cards

			deck.swapCards(command.slot, command.slot2);
		}

		return true;
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
