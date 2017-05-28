package royaleserver.database.service;

import royaleserver.database.entity.HomeChestEntity;

import javax.persistence.EntityManager;

public class HomeChestService {
	private final EntityManager entityManager;

	public HomeChestService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void put(HomeChestEntity entity) {
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
	}
}
