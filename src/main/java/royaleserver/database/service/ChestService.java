package royaleserver.database.service;

import royaleserver.database.entity.ChestEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ChestService {
	private final EntityManager entityManager;

	public ChestService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void beginResolve() {
		entityManager.getTransaction().begin();
	}

	public void endResolve() {
		entityManager.getTransaction().commit();
	}

	public ChestEntity resolve(String name) {
		ChestEntity entity;

		try {
			entity = (ChestEntity)entityManager.createNamedQuery("ChestEntity.getByName")
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException ignored) {
			entity = new ChestEntity();
			entity.setName(name);

			entity = entityManager.merge(entity);
		}

		return entity;
	}
}
