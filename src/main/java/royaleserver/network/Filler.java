package royaleserver.network;

import royaleserver.game.OpeningChest;
import royaleserver.game.PlayerCard;
import royaleserver.database.entity.ClanEntity;
import royaleserver.database.entity.HomeChestEntity;
import royaleserver.database.entity.HomeChestStatus;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.logic.ClanRole;
import royaleserver.network.protocol.server.commands.ChestOpenOk;
import royaleserver.network.protocol.server.commands.ClanCreateOk;
import royaleserver.network.protocol.server.commands.ClanLeaveOk;
import royaleserver.network.protocol.server.components.*;
import royaleserver.network.protocol.server.components.Deck;
import royaleserver.network.protocol.server.messages.ClanData;
import royaleserver.network.protocol.server.messages.HomeData;
import royaleserver.network.protocol.server.messages.HomeDataOwn;
import royaleserver.network.protocol.server.messages.HomeDataVisited;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

public final class Filler {
	private Filler() {}

	public static void fill(ClanData message, ClanEntity entity) {
		fill(message.header = new ClanHeader(), entity);

		message.description = entity.getDescription();
		message.members = new ClanMember[entity.getMembers().size()];

		int i = 0;
		for (PlayerEntity player : entity.getMembers()) {
			ClanMember member = new ClanMember();
			fill(member, player);
			message.members[i++] = member;
		}

		message.unknown_3 = 1; // 1
		message.unknown_4 = 0;
		message.unknown_5 = 234815; // 234815
		message.unknown_6 = 0;
		message.unknown_7 = 1494573487; // timestamp last request?
		message.unknown_8 = 1494832687; // timestamp ?
		message.unknown_9 = 4179457; // 4179457
		message.unknown_10 = 0;
		message.unknown_11 = 1;
	}

	public static void fill(ClanHeader message, ClanEntity entity) {
		message.id = entity.getId();
		message.name = entity.getName();
		message.badge = entity.getLogicBadge().getScid();

		switch (entity.getType()) {
		case OPEN:
			message.type = ClanHeader.CLAN_OPEN;
			break;
		case INVITE:
			message.type = ClanHeader.CLAN_INVITE;
			break;
		case CLOSED:
			message.type = ClanHeader.CLAN_CLOSED;
			break;
		}

		message.type = 1;

		message.numberOfMembers = entity.getMembers().size();
		message.score = entity.getScore();
		message.requiredScore = entity.getRequiredTrophies();
		message.unknown_7 = 0;
		message.unknown_8 = 0;
		message.currentRank = 0;
		message.unknown_10 = 50; // 50
		message.donations = entity.getDonationsPerWeek();
		message.unknown_12 = 0;
		message.unknown_13 = 1;
		message.unknown_14 = 12;
		message.unknown_15 = 57;
		message.region = 6;
		message.unknown_17 = 0;
	}

	public static void fill(ClanCreateOk message, ClanEntity entity) {
		message.clanId = entity.getId();
		message.name = entity.getName();
		message.badge = entity.getLogicBadge().getScid();
		message.unknown_3 = 1;
		message.accepted = 1;
	}

	public static void fill(ClanLeaveOk message, ClanEntity entity) {
		message.clanId = entity.getId();
	}

	public static void fill(ClanMember message, PlayerEntity entity) {
		message.avatarId = entity.getId();
		message.facebookId = "0";
		message.name = entity.getName();
		message.arena = entity.getLogicArena().getScid();
		message.role = (byte)entity.getLogicClanRole().getIndex();
		message.expLevel = entity.getLogicExpLevel().getIndex();
		message.score = entity.getTrophies();
		message.donations = 0; // TODO:
		message.unknown_8 = 0;
		message.currentRank = 4; // ?
		message.previousRank = 4; // ?
		message.clanChestCrowns = 0;
		message.unknown_12 = 0;
		message.unknown_13 = -64;
		message.unknown_14 = -64;
		message.unknown_15 = 0;
		message.unknown_16 = 4;
		message.homeID = entity.getId();
	}

	public static void fill(PlayerClan message, ClanEntity entity, ClanRole role) {
		message.id = entity.getId();
		message.name = entity.getName();
		message.badge = entity.getLogicBadge();
		message.role = role;
	}

	public static void fill(HomeData message, PlayerEntity entity) {
		message.isMyProfile = false;

		message.homeId = entity.getId();
		message.name = entity.getName();
		message.nameChangesCount = 0;

		if (entity.getClan() != null) {
			fill(message.clan = new PlayerClan(), entity.getClan(), entity.getLogicClanRole());
		} else {
			message.clan = null;
		}

		message.gold = entity.getGold();
		message.gems = message.freeGems = entity.getGems();

		message.level = entity.getLogicExpLevel();
		message.lastLevel = entity.getLogicLastExpLevel();
		message.levelExperience = entity.getExpLevelExperience();

		message.arena = entity.getLogicArena();
		message.lastArena = entity.getLogicArena(); // TODO:
		message.trophies = entity.getTrophies();
		message.highestTrophies = entity.getTrophies(); // TODO:
		message.legendaryTrophies = 0; // TODO:

		message.wins = 0; // TODO:
		message.threeCrownsWin = 0; // TODO:
		message.looses = 0; // TODO:
		message.cardsGiven = 0; // TODO:
		message.cardsFound = 0; // TODO:
		message.tournamentCardsWon = 0; // TODO:
		message.challengeCardsWon = 0; // TODO:
		message.challengeMaxWins = 0; // TODO:
		message.favouriteCard = null; // TODO:
	}

	public static void fill(HomeDataOwn message, PlayerEntity entity, royaleserver.game.Deck currentDeck,
	                        Collection<PlayerCard> cardsAfterDeck, Collection<royaleserver.game.Deck> decks) {
		fill((HomeData)message, entity);
		message.isMyProfile = true;
		message.giveSeasonReward = false;

		message.accountCreatedTime = (int)(entity.getRegisteredDate().getTime() / 1000);
		message.loginTime = (int)(System.currentTimeMillis() / 1000);
		message.homeChests = new HomeChest[entity.getHomeChests().size()];

		int i;

		i = 0;
		List<HomeChestEntity> homeChests = new ArrayList<>(entity.getHomeChests());
		homeChests.sort(Comparator.comparingInt(HomeChestEntity::getSlot));
		message.homeChests = new HomeChest[homeChests.size()];
		for (HomeChestEntity homeChestEntity : homeChests) {
			HomeChest homeChest = new HomeChest();
			homeChest.slot = homeChestEntity.getSlot();
			homeChest.chest = homeChestEntity.getLogicChest();
			switch (homeChestEntity.getStatus()) {
			case IDLE:
				homeChest.status = HomeChest.STATUS_STATIC;
				break;
			case OPENING:
				if (homeChestEntity.getOpenEnd().getTime() < System.currentTimeMillis()) {
					homeChestEntity.setStatus(HomeChestStatus.OPENED);
				} else {
					homeChest.status = HomeChest.STATUS_OPENING;
					homeChest.ticksToOpen = (int)((homeChestEntity.getOpenEnd().getTime() - System.currentTimeMillis()) / 50); // Convert millis to ticks
					break;
				}
			case OPENED:
				homeChest.status = HomeChest.STATUS_OPENED;
				break;
			}

			message.homeChests[i++] = homeChest;
		}

		message.cardsAfterDeck = new Card[cardsAfterDeck.size()];
		i = 0;
		for (PlayerCard playerCard : cardsAfterDeck) {
			Card card = new Card();
			card.card = playerCard.getCard();
			card.level = playerCard.getLevel();
			card.count = playerCard.getCount();

			message.cardsAfterDeck[i++] = card;
		}

		message.currentDeck = new Deck();
		message.currentDeck.cards = new Card[royaleserver.game.Deck.DECK_CARDS_COUNT];

		for (i = 0; i < royaleserver.game.Deck.DECK_CARDS_COUNT; i++) {
			Card card = new Card();
			PlayerCard deckCard = currentDeck.getCard(i);
			if (deckCard != null) {
				card.card = deckCard.getCard();
				card.level = deckCard.getLevel();
				card.count = deckCard.getCount();
			} else {
				card.card = i == 0 ? null : message.currentDeck.cards[i - 1].card;
				card.label = 0;
				card.count = 0;
			}

			message.currentDeck.cards[i] = card;
		}

		message.decks = new Deck[decks.size()];

		i = 0;
		for (royaleserver.game.Deck playerDeck : decks) {
			Deck deck = new Deck();
			deck.cards = new Card[royaleserver.game.Deck.DECK_CARDS_COUNT];
			for (int j = 0; j < royaleserver.game.Deck.DECK_CARDS_COUNT; ++j) {
				PlayerCard playerCard = playerDeck.getCard(j);
				if (playerCard != null) {
					Card card = new Card();
					card.card = playerCard.getCard();
					deck.cards[j] = card;
				} else {
					deck.cards[j] = null;
				}
			}

			message.decks[i] = deck;
			++i;
		}

		message.offers = new String[]{}; // TODO:
		message.challenges = new String[]{}; // TODO:

		message.shopCards = new Card[6]; // TODO:

		message.shopCards[0] = new Card();
		message.shopCards[0].card = royaleserver.logic.Card.by("Goblins");

		message.shopCards[1] = new Card();
		message.shopCards[1].card = royaleserver.logic.Card.by("Giant");

		message.shopCards[2] = new Card();
		message.shopCards[2].card = royaleserver.logic.Card.by("Zap");

		message.shopCards[3] = new Card();
		message.shopCards[3].card = royaleserver.logic.Card.by("Heal");

		message.shopCards[4] = new Card();
		message.shopCards[4].card = royaleserver.logic.Card.by("Balloon");

		message.shopCards[5] = new Card();
		message.shopCards[5].card = royaleserver.logic.Card.by("Log");
		message.shopCards[5].boughtTimes = 1;
	}

	public static void fill(HomeDataVisited message, PlayerEntity entity, boolean isMyProfile) {
		fill((HomeData)message, entity);
		message.isMyProfile = isMyProfile;

		message.place = 0; // TODO:

		message.deck = new Deck(); // TODO:
		message.deck.cards = new Card[8];
		for (int i = 0; i < message.deck.cards.length; i++) {
			Card card = new Card();
			card.card = royaleserver.logic.Card.byDB(i + 1); // Temponary solution
			card.level = 1;

			message.deck.cards[i] = card;
		}
	}

	public static void fill(ChestOpenOk command, OpeningChest chest) {
		command.gold = chest.gold();
		command.gems = chest.gems();

		command.items = new ChestItem[chest.cards().size() * chest.optionSize()];
		int i = 0;
		for (OpeningChest.CardStack[] stack : chest.cards()) {
			for (OpeningChest.CardStack card : stack) {
				ChestItem item = new ChestItem();
				item.card = card.card;
				item.count = card.count;
				item.cardOrder = (byte)i;
				command.items[i] = item;

				++i;
			}
		}

		command.isDraft = chest.optionSize() != 1;

		command.unknown_7 = 4;
		command.unknown_8 = 2;
	}
}
