package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.UnlockCodeEntity;
import royaleserver.database.util.Transaction;
import royaleserver.utils.StringUtils;

public class UnlockCodeService extends Service {
	public UnlockCodeService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	/**
	 * Generate, store, and return random unlock key.
	 * @return Generated key
	 */
	public String generate() {
		String code = StringUtils.randomString(UnlockCodeEntity.CODE_LENGTH);

		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			session.merge(new UnlockCodeEntity(code));
			transaction.commit();
		}

		return code;
	}

	public boolean use(String code) {
		boolean result;
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			try {
				result = session.createNamedQuery("UnlockCodeEntity.use")
						.setParameter("code", code)
						.executeUpdate() == 1;
				transaction.commit();
			} catch (Throwable e) {
				e.printStackTrace();
				result = false;
			}
		}

		return result;
	}
}
