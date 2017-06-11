package royaleserver.database.entity;

import royaleserver.logic.Card;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player_card", indexes = {
		@Index(unique = true, columnList = "player_id,card_id")
})
public class PlayerCardEntity implements Serializable {
	@Id
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "player_id", referencedColumnName = "id")
	private PlayerEntity player;

	@Id
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "card_id", referencedColumnName = "id")
	private CardEntity card;

	@Column(nullable = false)
	private int level = 0;

	@Column(nullable = false)
	private int count = 0;

	public PlayerEntity getPlayer() {
		return player;
	}

	public PlayerCardEntity setPlayer(PlayerEntity player) {
		this.player = player;
		return this;
	}

	public CardEntity getCard() {
		return card;
	}

	public PlayerCardEntity setCard(CardEntity card) {
		this.card = card;
		return this;
	}

	public int getLevel() {
		return level;
	}

	public PlayerCardEntity setLevel(int level) {
		this.level = level;
		return this;
	}

	public int getCount() {
		return count;
	}

	public PlayerCardEntity setCount(int count) {
		this.count = count;
		return this;
	}

	public Card getLogicCard() {
		return Card.byDB(card.getId());
	}

	public PlayerCardEntity setLogicCard(Card card) {
		return setCard(card.getDbEntity());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PlayerCardEntity)) {
			return false;
		}

		PlayerCardEntity that = (PlayerCardEntity)o;

		if (player != null ? !player.equals(that.player) : that.player != null) {
			return false;
		}
		return card != null ? card.equals(that.card) : that.card == null;
	}

	@Override
	public int hashCode() {
		int result = player != null ? player.hashCode() : 0;
		result = 31 * result + (card != null ? card.hashCode() : 0);
		return result;
	}
}
