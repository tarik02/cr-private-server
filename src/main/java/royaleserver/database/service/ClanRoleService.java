package royaleserver.database.service;

import royaleserver.database.entity.ClanRoleEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

public class ClanRoleService {
	private final EntityManager entityManager;

	public ClanRoleService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public void beginResolve() {
		entityManager.getTransaction().begin();
	}

	public void endResolve() {
		entityManager.getTransaction().commit();
	}

	public ClanRoleEntity resolve(String name) {
		ClanRoleEntity entity;

		try {
			entity = (ClanRoleEntity)entityManager.createNamedQuery("ClanRoleEntity.getByName")
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException ignored) {
			entity = new ClanRoleEntity();
			entity.setName(name);

			entity = entityManager.merge(entity);
		}

		return entity;
	}
}
