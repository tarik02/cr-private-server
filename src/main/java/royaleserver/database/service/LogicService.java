package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.LogicEntity;
import royaleserver.database.util.Transaction;

import java.util.List;
import java.util.Map;

public abstract class LogicService<EntityType extends LogicEntity> extends Service {
	private final Class<EntityType> entityClass;
	private final String entityClassName;

	public LogicService(SessionFactory sessionFactory, Class<EntityType> entityClass) {
		super(sessionFactory);

		this.entityClass = entityClass;
		this.entityClassName = entityClass.getSimpleName();
	}
	
	protected abstract EntityType createEntity(String name);

	public List<EntityType> all() {
		try (Session session = session()) {
			return session.createNamedQuery(entityClassName + ".all", entityClass).getResultList();
		}
	}

	public void store(Map<String, Long> entries) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (Map.Entry<String, Long> entry : entries.entrySet()) {
				EntityType entity = createEntity(entry.getKey());
				Long id = (Long)session.save(entity);
				entry.setValue(id);
			}

			transaction.commit();
		}
	}
}
