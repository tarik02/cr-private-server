package royaleserver.database.provider;

import royaleserver.database.data.PlayerData;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLDataProvider extends DataProvider {
	public static class LoginData {
		public String host = "localhost";
		public short port = 3306;

		public String user = "root";
		public String password = "";

		public String database = "royale-server";
	}

	private Connection connection;

	public MySQLDataProvider() {
		this(new LoginData());
	}

	public MySQLDataProvider(LoginData loginData) {
		if (loginData == null) {
			throw new NullPointerException("loginData");
		}

		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://");
		sb.append(loginData.host);
		sb.append(":");
		sb.append(loginData.port);
		sb.append("/");
		sb.append(loginData.database);

		try {
			connection = DriverManager.getConnection(sb.toString(), loginData.user, loginData.password);
		} catch (SQLException ignored) {}
	}

	@Override
	public boolean fetchPlayerData(int id, PlayerData playerData) {
		try {
			PreparedStatement st = connection.prepareStatement("SELECT * FROM `players` WHERE `id`=?");
			st.setInt(1, id);
			ResultSet result = st.executeQuery();
			if (result.next()) {
				
				return true;
			}
		} catch (SQLException ignored) {}

		return false;
	}
}
