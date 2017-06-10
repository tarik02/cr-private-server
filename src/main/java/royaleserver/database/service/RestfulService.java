package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.util.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RestfulService<Id extends Serializable, Entity> extends Service {
	public RestfulService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Add the entity to database.
	 * @param entity Entity to add
	 * @return generated id
	 */
	public final Id add(Entity entity) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			Id result = (Id)session.save(entity);
			transaction.commit();
			return result;
		}
	}

	/**
	 * Add the entities to database.
	 * @param entities Entities to add
	 * @return generated id's
	 */
	public final List<Id> add(Entity[] entities) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			List<Id> ids = new ArrayList<>(entities.length);
			for (Entity entity : entities) {
				ids.add((Id)session.save(entity));
			}

			transaction.commit();
			return Collections.unmodifiableList(ids);
		}
	}

	public final void update(Entity entity) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			session.save(entity);
			transaction.commit();
		}
	}

	public final void update(Entity[] entities) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (Entity entity : entities) {
				session.save(entity);
			}

			transaction.commit();
		}
	}

	public final void delete(Entity entity) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			session.delete(entity);
			transaction.commit();
		}
	}

	public final void delete(Entity[] entities) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (Entity entity : entities) {
				session.delete(entity);
			}

			transaction.commit();
		}
	}
}
