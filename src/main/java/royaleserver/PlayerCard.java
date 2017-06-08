package royaleserver;

import royaleserver.logic.Card;

public final class PlayerCard {
	private Card card;
	private int level;
	private int count;

	public PlayerCard(Card card, int level, int count) {
		this.card = card;
		this.level = level;
		this.count = count;
	}

	public Card getCard() {
		return card;
	}

	public int getLevel() {
		return level;
	}

	public PlayerCard setLevel(int level) {
		this.level = level;
		return this;
	}

	public int getCount() {
		return count;
	}

	public PlayerCard setCount(int count) {
		this.count = count;
		return this;
	}

	public PlayerCard addCount(int count) {
		this.count += count;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PlayerCard)) {
			return false;
		}

		PlayerCard that = (PlayerCard)o;

		return card.equals(that.card);
	}

	@Override
	public int hashCode() {
		return card.hashCode();
	}
}
