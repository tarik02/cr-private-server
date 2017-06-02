package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.AssetEntity;
import royaleserver.database.util.Transaction;

import javax.persistence.NoResultException;
import java.util.Date;

public class AssetService extends Service {
	public AssetService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public AssetEntity get(String name) {
		AssetEntity entity;

		try (Session session = getSession()) {
			try {
				entity = session.createNamedQuery("AssetEntity.byName", AssetEntity.class)
						.setParameter("name", name)
						.getSingleResult();
			} catch (NoResultException ignored) {
				entity = new AssetEntity();
				entity.setName(name);
				entity.setLastUpdated(new Date(System.currentTimeMillis()));
			}
		}

		return entity;
	}

	public void update(AssetEntity entity) {
		entity.setLastUpdated(new Date(System.currentTimeMillis()));

		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			session.merge(entity);
			transaction.commit();
		}
	}
}
