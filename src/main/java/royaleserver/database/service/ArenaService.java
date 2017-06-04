package royaleserver.database.service;

import org.hibernate.SessionFactory;
import royaleserver.database.entity.ArenaEntity;

public class ArenaService extends LogicService<ArenaEntity> {
	public ArenaService(SessionFactory sessionFactory) {
		super(sessionFactory, ArenaEntity.class);
	}

	@Override
	protected ArenaEntity createEntity(String name) {
		return new ArenaEntity(name);
	}
}
