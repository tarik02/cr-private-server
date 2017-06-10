package royaleserver.database.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player_deck_card", indexes = {
		@Index(unique = true, columnList = "player_id,number,card_slot")
})
public class PlayerDeckCardEntity implements Serializable {
	@Id
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "player_id", referencedColumnName = "id")
	private PlayerEntity player;

	@Id
	private int number;

	@Id
	@Column(name = "card_slot")
	private int cardSlot;

	@ManyToOne(optional = true)
	private CardEntity card;

	public PlayerDeckCardEntity() {
	}

	public PlayerDeckCardEntity(PlayerEntity player, int number, int cardSlot, CardEntity card) {
		this.player = player;
		this.number = number;
		this.cardSlot = cardSlot;
		this.card = card;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	public int getNumber() {
		return number;
	}

	public CardEntity getCard() {
		return card;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PlayerDeckCardEntity)) {
			return false;
		}

		PlayerDeckCardEntity that = (PlayerDeckCardEntity)o;

		if (number != that.number) {
			return false;
		}
		if (cardSlot != that.cardSlot) {
			return false;
		}
		return player.equals(that.player);
	}

	@Override
	public int hashCode() {
		int result = player.hashCode();
		result = 31 * result + number;
		result = 31 * result + cardSlot;
		return result;
	}
}
