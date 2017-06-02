package royaleserver.database.service;

import org.hibernate.SessionFactory;
import royaleserver.database.entity.CardEntity;

public class CardService extends LogicService<CardEntity> {
	public CardService(SessionFactory sessionFactory) {
		super(sessionFactory, CardEntity.class);
	}

	@Override
	protected CardEntity createEntity(String name) {
		return new CardEntity(name);
	}
}
