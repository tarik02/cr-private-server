package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.database.entity.ExpLevelEntity;

import java.util.ArrayList;
import java.util.List;

public final class ExpLevel extends DBLogic<ExpLevelEntity> {
	private int index;
	private int numericName;
	private int expToNextLevel;
	private int totalExp;

	private ExpLevel() {}

	public int getIndex() {
		return index;
	}

	public int getNumericName() {
		return numericName;
	}

	public int getExpToNextLevel() {
		return expToNextLevel;
	}

	public int getTotalExp() {
		return totalExp;
	}

	private static boolean initialized = false;
	private static List<ExpLevel> values = new ArrayList<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		Table csv_levels = server.getAssetManager().open("csv_logic/exp_levels.csv").csv();
		Column csv_Name = csv_levels.getColumn("Name");
		Column csv_ExpToNextLevel = csv_levels.getColumn("ExpToNextLevel");

		int i = 1, totalExp = 0;
		for (Row csv_level : csv_levels.getRows()) {
			ExpLevel expLevel = new ExpLevel();

			expLevel.index = i++;
			expLevel.name = String.valueOf(expLevel.numericName = csv_level.getValue(csv_Name).asInt());
			expLevel.expToNextLevel = csv_level.getValue(csv_ExpToNextLevel).asInt();
			expLevel.totalExp = totalExp;
			totalExp += expLevel.expToNextLevel;

			values.add(expLevel);
		}

		init(values, server.getDataManager().getExpLevelService());

		initialized = true;
	}

	public static ExpLevel by(int name) {
		for (ExpLevel expLevel : values) {
			if (expLevel.numericName == name) {
				return expLevel;
			}
		}

		return null;
	}

	public static ExpLevel byIndex(int index) {
		for (ExpLevel expLevel : values) {
			if (expLevel.index == index) {
				return expLevel;
			}
		}

		return null;
	}

	public static ExpLevel byDB(long id) {
		for (ExpLevel expLevel : values) {
			if (expLevel.dbId == id) {
				return expLevel;
			}
		}

		return null;
	}
}
