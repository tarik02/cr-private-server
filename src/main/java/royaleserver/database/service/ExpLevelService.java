package royaleserver.database.service;

import org.hibernate.SessionFactory;
import royaleserver.database.entity.ExpLevelEntity;

public class ExpLevelService extends LogicService<ExpLevelEntity> {
	public ExpLevelService(SessionFactory sessionFactory) {
		super(sessionFactory, ExpLevelEntity.class);
	}

	@Override
	protected ExpLevelEntity createEntity(String name) {
		return new ExpLevelEntity(name);
	}
}
