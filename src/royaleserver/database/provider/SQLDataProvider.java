package royaleserver.database.provider;

import royaleserver.database.data.PlayerData;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

public class SQLDataProvider extends DataProvider {
	private static Logger logger = LogManager.getLogger(SQLDataProvider.class);

	private Connection connection;

	public SQLDataProvider(String dsn, String user, String password) {
		try {
			connection = DriverManager.getConnection(dsn, user, password);
		} catch (SQLException ignored) {
			ignored.printStackTrace();
		}
	}

	@Override
	public boolean applyMigration(String name) {
		String[] migrationQueries;

		try {
			InputStream migration = SQLDataProvider.class.getResourceAsStream("/" + name.replaceAll("\\.", "/") + ".sql");
			byte[] migrationBytes = new byte[migration.available()];
			migration.read(migrationBytes);
			migration.close();
			String migrationString = new String(migrationBytes);
			migrationBytes = null;
			migrationQueries = migrationString.split(";");
			migrationString = null;
		} catch (IOException e) {
			logger.error("Failed to read migration.", e);
			return false;
		}

		try {
			Statement statement = connection.createStatement();
			for (String query : migrationQueries) {
				statement.addBatch(query);
			}
			statement.executeBatch();
			return true;
		} catch (SQLException e) {
			logger.error("Failed to apply migration.", e);
			return false;
		}
	}

	@Override
	public int fetchVersion() {
		try {
			ResultSet result = connection.createStatement().executeQuery("SELECT version FROM server");
			if (result.next()) {
				return result.getInt(1);
			}
		} catch (SQLException ignored) {}

		return 0;
	}

	@Override
	public void updateVersion(int version) {
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE server SET version=?");
			statement.setInt(1, version);
			statement.execute();
		} catch (SQLException e) {
			logger.error("Failed to update version.", e);
		}
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
		} catch (SQLException e) {
			logger.error("Failed to fetch player data.", e);
		}

		return false;
	}
}
