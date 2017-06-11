package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.util.Transaction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
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
	public final Collection<Id> add(Collection<Entity> entities) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			List<Id> ids = new ArrayList<>(entities.size());
			for (Entity entity : entities) {
				ids.add((Id)session.save(entity));
			}

			transaction.commit();
			return Collections.unmodifiableList(ids);
		}
	}

	public final void update(Entity entity) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			session.update(entity);
			transaction.commit();
		}
	}

	public final void update(Collection<Entity> entities) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (Entity entity : entities) {
				session.update(entity);
			}

			transaction.commit();
		}
	}

	public final void merge(Entity entity) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			session.merge(entity);
			transaction.commit();
		}
	}

	public final void merge(Collection<Entity> entities) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (Entity entity : entities) {
				session.merge(entity);
			}

			transaction.commit();
		}
	}

	public final void merge(Collection<Entity> entitiesAdd, Collection<Entity> entitiesUpdate) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (Entity entity : entitiesAdd) {
				session.save(entity);
			}

			for (Entity entity : entitiesUpdate) {
				session.update(entity);
			}

			transaction.commit();
		}
	}

	public final void saveOrUpdate(Entity entity) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			session.saveOrUpdate(entity);
			transaction.commit();
		}
	}

	public final void saveOrUpdate(Collection<Entity> entities) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (Entity entity : entities) {
				session.saveOrUpdate(entity);
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

	public final void delete(Collection<Entity> entities) {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			for (Entity entity : entities) {
				session.delete(entity);
			}

			transaction.commit();
		}
	}
}
