package royaleserver.config;

public class Config {
	public static final int CONFIG_VERSION = 2;
	public static final String CONTENT_HASH = "e99dcd87d0f3ba976efd982ef51a7f393463aeca";

	public Integer version = 0;
	public Server server = new Server();
	public Database database = new Database();
}
