package royaleserver.database.entity;

import royaleserver.logic.Card;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "player_deck_card", indexes = {
		@Index(unique = true, columnList = "player_id,deck_slot,card_slot")
})
public class PlayerDeckCardEntity implements Serializable {
	@Id
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "player_id", referencedColumnName = "id")
	private PlayerEntity player;

	@Id
	@Column(name = "deck_slot")
	private int deckSlot;

	@Id
	@Column(name = "card_slot")
	private int cardSlot;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private CardEntity card;

	public PlayerDeckCardEntity() {
	}

	public PlayerDeckCardEntity(PlayerEntity player, int deckSlot, int cardSlot, CardEntity card) {
		this.player = player;
		this.deckSlot = deckSlot;
		this.cardSlot = cardSlot;
		this.card = card;
	}

	public PlayerEntity getPlayer() {
		return player;
	}

	public int getDeckSlot() {
		return deckSlot;
	}

	public int getCardSlot() {
		return cardSlot;
	}

	public CardEntity getCard() {
		return card;
	}

	public Card getLogicCard() {
		return Card.byDB(card.getId());
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

		if (deckSlot != that.deckSlot) {
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
		result = 31 * result + deckSlot;
		result = 31 * result + cardSlot;
		return result;
	}
}
