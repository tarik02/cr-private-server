package royaleserver.database.service;

import royaleserver.database.entity.ClanBadgeEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ClanBadgeService {
	private final EntityManager entityManager;

	public ClanBadgeService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void beginResolve() {
		entityManager.getTransaction().begin();
	}

	public void endResolve() {
		entityManager.getTransaction().commit();
	}

	public ClanBadgeEntity resolve(String name) {
		ClanBadgeEntity entity;

		try {
			entity = (ClanBadgeEntity)entityManager.createNamedQuery("ClanBadgeEntity.getByName")
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException ignored) {
			entity = new ClanBadgeEntity();
			entity.setName(name);

			entity = entityManager.merge(entity);
		}

		return entity;
	}
}
