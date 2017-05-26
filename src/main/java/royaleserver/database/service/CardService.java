package royaleserver.database.service;

import royaleserver.database.entity.CardEntity;
import royaleserver.logic.Card;

import javax.persistence.EntityManager;
import java.util.List;

public class CardService {
	private final EntityManager entityManager;

	public CardService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void update() {
		List<Card> cards = Card.cards();

		for (Card card : cards) {
			CardEntity cardEntity = new CardEntity();
			cardEntity
					.setType(card.getType())
					.setIndex(card.getIndex())
					.setScid(card.getScid())
					.setName(card.getName());
			entityManager.getTransaction().begin();
			entityManager.merge(cardEntity);
			entityManager.getTransaction().commit();
		}
	}
}
