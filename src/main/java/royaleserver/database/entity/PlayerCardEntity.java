package royaleserver.database.entity;

import royaleserver.logic.Card;

import javax.persistence.*;

@Entity
@Table(name = "player_card")
public class PlayerCardEntity implements java.io.Serializable {
	@Column(name = "card_id")
	private long cardId;

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PlayerCardEntity)) {
			return false;
		}

		PlayerCardEntity that = (PlayerCardEntity)o;

		if (!player.equals(that.player)) {
			return false;
		}
		return card.equals(that.card);
	}

	@Override
	public int hashCode() {
		int result = player.hashCode();
		result = 31 * result + card.hashCode();
		return result;
	}
}
