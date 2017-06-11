package royaleserver;

import royaleserver.database.entity.PlayerDeckCardEntity;

public final class Deck {
	public static final int DECK_CARDS_COUNT = 8;

	private final PlayerCard[] cards = new PlayerCard[DECK_CARDS_COUNT];
	private final PlayerDeckCardEntity[] entities = new PlayerDeckCardEntity[DECK_CARDS_COUNT];
	private boolean changed = false;

	public Deck() {

	}

	public boolean changed() {
		return changed;
	}

	/**
	 * Marks changed to false
	 * @return old changed value
	 */
	public boolean markUnchanged() {
		boolean changed = this.changed;
		this.changed = false;
		return changed;
	}

	public boolean hasCard(PlayerCard card) {
		for (int i = 0; i < DECK_CARDS_COUNT; ++i) {
			if (cards[i] == card) {
				return true;
			}
		}

		return false;
	}

	public PlayerCard getCard(int slot) {
		if (slot < 0 || slot >= DECK_CARDS_COUNT) {
			throw new IllegalArgumentException("slot");
		}

		return cards[slot];
	}

	public void swapCards(int slot1, int slot2) {
		changed = true;

		if (slot1 < 0 || slot1 >= DECK_CARDS_COUNT) {
			throw new IllegalArgumentException("slot1");
		}

		if (slot2 < 0 || slot2 >= DECK_CARDS_COUNT) {
			throw new IllegalArgumentException("slot2");
		}

		PlayerCard temp = cards[slot1];
		cards[slot1] = cards[slot2];
		cards[slot2] = temp;
	}

	public PlayerCard swapCard(int slot, PlayerCard targetCard) {
		changed = true;

		if (slot < 0 || slot >= DECK_CARDS_COUNT) {
			throw new IllegalArgumentException("slot");
		}

		if (targetCard == null) {
			throw new IllegalArgumentException("targetCard");
		}

		PlayerCard result = cards[slot];
		cards[slot] = targetCard;
		return result;
	}

	public PlayerDeckCardEntity getEntity(int slot) {
		if (slot < 0 || slot >= DECK_CARDS_COUNT) {
			throw new IllegalArgumentException("slot");
		}

		return entities[slot];
	}

	public void setEntity(int slot, PlayerDeckCardEntity entity) {
		if (slot < 0 || slot >= DECK_CARDS_COUNT) {
			throw new IllegalArgumentException("slot");
		}

		entities[slot] = entity;
	}
}
