package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.PlayerCardEntity;
import royaleserver.database.util.Transaction;

public class PlayerCardService extends Service {
	public PlayerCardService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void add(PlayerCardEntity entity) {
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			session.merge(entity);
			transaction.commit();
		}
	}
}
