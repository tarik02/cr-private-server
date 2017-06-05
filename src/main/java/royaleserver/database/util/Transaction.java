package royaleserver.database.util;

public abstract class Transaction implements AutoCloseable {
	public abstract void commit();
	public abstract void rollback();
	public abstract void close();
}
