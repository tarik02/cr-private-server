package royaleserver.database.service;

import org.hibernate.SessionFactory;
import royaleserver.database.entity.ChestEntity;

public class ChestService extends LogicService<ChestEntity> {
	public ChestService(SessionFactory sessionFactory) {
		super(sessionFactory, ChestEntity.class);
	}

	@Override
	protected ChestEntity createEntity(String name) {
		return new ChestEntity(name);
	}
}
