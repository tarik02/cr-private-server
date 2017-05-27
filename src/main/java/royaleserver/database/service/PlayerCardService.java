package royaleserver.database.service;

import royaleserver.database.entity.PlayerCardEntity;

import javax.persistence.EntityManager;

public class PlayerCardService {
	private final EntityManager entityManager;

	public PlayerCardService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void add(PlayerCardEntity entity) {
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
	}
}
