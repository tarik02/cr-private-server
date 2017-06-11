package royaleserver.database.service;

import org.hibernate.SessionFactory;
import royaleserver.database.entity.PlayerDeckCardEntity;

public class PlayerDeckCardService extends RestfulService<Long, PlayerDeckCardEntity> {
	public PlayerDeckCardService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
