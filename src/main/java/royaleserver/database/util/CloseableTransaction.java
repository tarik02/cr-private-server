package royaleserver.database.util;

import org.hibernate.Transaction;

public class CloseableTransaction extends royaleserver.database.util.Transaction {
	private final Transaction transaction;

	public CloseableTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public void commit() {
		transaction.commit();
	}

	@Override
	public void rollback() {
		transaction.rollback();
	}

	@Override
	public void close() {
		if (transaction.isActive()) {
			transaction.rollback();
		}
	}
}
