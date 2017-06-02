package royaleserver.database.service;

import org.hibernate.SessionFactory;
import royaleserver.database.entity.ClanRoleEntity;

public class ClanRoleService extends LogicService<ClanRoleEntity> {
	public ClanRoleService(SessionFactory sessionFactory) {
		super(sessionFactory, ClanRoleEntity.class);
	}

	@Override
	protected ClanRoleEntity createEntity(String name) {
		return new ClanRoleEntity(name);
	}
}
