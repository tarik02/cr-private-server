package royaleserver.database.service;

import royaleserver.database.entity.ExpLevelEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ExpLevelService {
	private final EntityManager entityManager;

	public ExpLevelService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void beginResolve() {
		entityManager.getTransaction().begin();
	}

	public void endResolve() {
		entityManager.getTransaction().commit();
	}

	public ExpLevelEntity resolve(int name) {
		ExpLevelEntity entity;

		try {
			entity = (ExpLevelEntity)entityManager.createNamedQuery("ExpLevelEntity.getByName")
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException ignored) {
			entity = new ExpLevelEntity();
			entity.setName(name);

			entity = entityManager.merge(entity);
		}

		return entity;
	}
}
