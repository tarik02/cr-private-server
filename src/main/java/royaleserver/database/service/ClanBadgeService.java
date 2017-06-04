package royaleserver.database.service;

import org.hibernate.SessionFactory;
import royaleserver.database.entity.ClanBadgeEntity;

public class ClanBadgeService extends LogicService<ClanBadgeEntity> {
	public ClanBadgeService(SessionFactory sessionFactory) {
		super(sessionFactory, ClanBadgeEntity.class);
	}

	@Override
	protected ClanBadgeEntity createEntity(String name) {
		return new ClanBadgeEntity(name);
	}
}
