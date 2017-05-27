package royaleserver.database.service;

import royaleserver.database.entity.AssetEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Date;

public class AssetService {
	private final EntityManager entityManager;

	public AssetService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public AssetEntity get(String name) {
		AssetEntity entity;

		try {
			entity = (AssetEntity)entityManager.createNamedQuery("getAssetByName")
					.setParameter("name", name)
					.getSingleResult();
		} catch (NoResultException ignored) {
			entity = new AssetEntity();
			entity.setName(name);
			entity.setLastUpdated(new Date(System.currentTimeMillis()));
		}

		return entity;
	}

	public void update(AssetEntity entity) {
		entity.setLastUpdated(new Date(System.currentTimeMillis()));

		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
	}
}
