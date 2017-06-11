package royaleserver.database.service;

import org.hibernate.SessionFactory;
import royaleserver.database.entity.PlayerCardEntity;

public class PlayerCardService extends RestfulService<Long, PlayerCardEntity> {
	public PlayerCardService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}
