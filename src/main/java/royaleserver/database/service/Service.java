package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.util.CloseableTransaction;
import royaleserver.database.util.Transaction;

public abstract class Service {
	protected final SessionFactory sessionFactory;

	protected Service(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.openSession();
	}

	protected Transaction transaction(Session session) {
		return new CloseableTransaction(session.beginTransaction());
	}
}
