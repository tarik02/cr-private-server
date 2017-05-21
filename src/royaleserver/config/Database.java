package royaleserver.config;

public class Database {
	public String provider = "mysql";
	public MySQLProvider mysql = new MySQLProvider();
	public SQLiteProvider sqlite = new SQLiteProvider();
}
