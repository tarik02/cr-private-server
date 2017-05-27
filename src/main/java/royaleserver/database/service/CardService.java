package royaleserver.database.service;

import royaleserver.database.entity.CardEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class CardService {
	private final EntityManager entityManager;

	public CardService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void beginResolve() {
		entityManager.getTransaction().begin();
	}

	public void endResolve() {
		entityManager.getTransaction().commit();
	}

	public CardEntity resolve(String name) {
		CardEntity entity;

		try {
			entity = (CardEntity)entityManager.createNamedQuery("getByName")
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException ignored) {
			entity = new CardEntity();
			entity.setName(name);

			entityManager.merge(entity);
		}

		return entity;
	}
}
