package royaleserver.game;

import royaleserver.logic.Card;
import royaleserver.database.entity.PlayerCardEntity;

public final class PlayerCard {
	private Card card;
	private int level;
	private int count;

	private PlayerCardEntity entity;

	public PlayerCard(Card card, int level, int count) {
		this(card, level, count, null);
	}

	public PlayerCard(Card card, int level, int count, PlayerCardEntity entity) {
		this.card = card;
		this.level = level;
		this.count = count;
		this.entity = entity;
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

	public PlayerCardEntity getEntity() {
		return entity;
	}

	public void setEntity(PlayerCardEntity entity) {
		this.entity = entity;
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
