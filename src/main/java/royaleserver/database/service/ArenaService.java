package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import royaleserver.database.entity.ArenaEntity;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

public class ArenaService extends Service {
	public ArenaService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<ArenaEntity> all() {
		try (Session session = getSession()) {
			return session.createNamedQuery("ArenaEntity.all", ArenaEntity.class).getResultList();
		}
	}

	public void store(Map<String, Long> entries) {
		try (Session session = getSession()) {
			try {
				session.getTransaction().begin();

				for (Map.Entry<String, Long> entry : entries) {
					entry.setValue(((ArenaEntity)session.merge(new ArenaEntity().setName(entry.getKey()))).getId());
				}

				session.getTransaction().commit();
			} catch (Throwable e) {
				session.getTransaction().rollback();
			}
		}
	}
}
