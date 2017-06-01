package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class Service {
	protected final SessionFactory sessionFactory;

	protected Service(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}
}
