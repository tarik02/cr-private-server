package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.HomeChestEntity;
import royaleserver.database.util.Transaction;

public class HomeChestService extends Service {
	public HomeChestService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void put(HomeChestEntity entity) {
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			session.merge(entity);
			transaction.commit();
		}
	}

	public void delete(HomeChestEntity entity) {
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			session.remove(entity);
			transaction.commit();
		}
	}
}
