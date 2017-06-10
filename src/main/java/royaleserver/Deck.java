package royaleserver;

public class Deck {
	public static final int DECK_CARDS_COUNT = 8;

	private final PlayerCard[] cards = new PlayerCard[DECK_CARDS_COUNT];

	public Deck() {

	}

	public void swapCards(int slot1, int slot2) {
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
}
