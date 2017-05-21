package royaleserver.database.provider;

import royaleserver.database.DataManager;
import royaleserver.database.model.Model;
import royaleserver.database.model.ModelInfo;
import royaleserver.database.model.PlayerModel;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;

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
				query = query.trim();
				if (query.length() != 0) {
					statement.addBatch(query);
				}
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
			ResultSet result = connection.createStatement().executeQuery("SELECT `version` FROM `server`");
			if (result.next()) {
				return result.getInt(1);
			}
		} catch (SQLException ignored) {}

		return 0;
	}

	@Override
	public void updateVersion(int version) {
		try {
			PreparedStatement statement = connection.prepareStatement("UPDATE `server` SET `version`=?");
			statement.setInt(1, version);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Failed to update version.", e);
		}
	}

	protected <T extends Model> T fetch(ModelInfo<T> modelInfo, ResultSet result, int[] keys) throws SQLException {
		Object[] values = new Object[modelInfo.keys.length];

		if (keys == null || keys.length == 0) {
			for (int i = 0; i < values.length; ++i) {
				values[i] = result.getString(1 + i);
			}
		} else {
			for (int i = 0; i < keys.length; ++i) {
				values[keys[i]] = result.getString(1 + i);
			}
		}

		try {
			return modelInfo.clazz.getDeclaredConstructor(Object[].class).newInstance(new Object[] {values});
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ignored) {ignored.printStackTrace();}
		return null;
	}

	protected String[] getKeys(ModelInfo modelInfo, int[] keys) {
		if (keys == null || keys.length == 0) {
			return null;
		}

		ArrayList<String> result = new ArrayList<>();
		for (int key : keys) {
			result.add(modelInfo.keys[key]);
		}

		return result.toArray(new String[0]);
	}

	protected void formatKeys(StringBuilder sb, ModelInfo modelInfo, int[] keys) {
		if (keys == null || keys.length == 0) {
			sb.append("*");
		} else {
			sb.append(modelInfo.keys[keys[0]]);
			for (int i = 1; i < keys.length; ++i) {
				sb.append(", ");
				sb.append(modelInfo.keys[keys[i]]);
			}
		}
	}

	public PlayerModel fetchPlayerById(int id, int[] keys) {
		ModelInfo<PlayerModel> modelInfo = DataManager.getModelInfo(PlayerModel.class);

		try {
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT ");
			formatKeys(sb, modelInfo, keys);
			sb.append(" FROM `players` WHERE `id`=?");

			PreparedStatement st = connection.prepareStatement(sb.toString());
			st.setInt(1, id);
			ResultSet result = st.executeQuery();
			if (result.next()) {
				return fetch(modelInfo, result, keys);
			}
		} catch (SQLException ignored) {
			ignored.printStackTrace();
		}

		return null;
	}
}
