package royaleserver.database.service;

import royaleserver.database.entity.ArenaEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ArenaService {
	private final EntityManager entityManager;

	public ArenaService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void beginResolve() {
		entityManager.getTransaction().begin();
	}

	public void endResolve() {
		entityManager.getTransaction().commit();
	}

	public ArenaEntity resolve(String name) {
		ArenaEntity entity;

		try {
			entity = (ArenaEntity)entityManager.createNamedQuery("ArenaEntity.getByName")
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException ignored) {
			entity = new ArenaEntity();
			entity.setName(name);

			entity = entityManager.merge(entity);
		}

		return entity;
	}
}
