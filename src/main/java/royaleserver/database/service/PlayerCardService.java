package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.PlayerCardEntity;
import royaleserver.database.util.Transaction;

public class PlayerCardService extends Service {
	public PlayerCardService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public void merge(PlayerCardEntity[] adds, PlayerCardEntity[] updates) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (PlayerCardEntity entity : adds) {
				session.save(entity);
			}

			for (PlayerCardEntity entity : updates) {
				session.update(entity);
			}

			transaction.commit();
		}
	}
}
