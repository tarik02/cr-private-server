package royaleserver.database.service;

import royaleserver.database.entity.AssetEntity;

import javax.persistence.EntityManager;
import java.sql.Timestamp;

public class AssetService {
	private final EntityManager entityManager;

	public AssetService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public AssetEntity get(String name) {
		return (AssetEntity)entityManager.createNamedQuery("getAssetByName")
				.setParameter("name", name)
				.getSingleResult();
	}

	public void update(AssetEntity entity) {
		entity.setLastUpdated(new Timestamp(System.currentTimeMillis()));

		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
	}
}
