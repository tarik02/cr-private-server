package royaleserver;

public class ServerException extends Exception {
	public ServerException(String message) {
		super(message);
	}

	public ServerException(String message, Throwable e) {
		super(message, e);
	}
}
