package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.utils.SCID;

import java.util.HashMap;
import java.util.Map;

public class GameMode {
	private int index;
	private SCID scid;

	private String name;
	private int overtimeSeconds;
	private float elixirProductionMultiplier;
	private float elixirProductionOvertimeMultiplier;
	private boolean useStartingElixir;
	private int startingElixir;
	private boolean team;

	private GameMode() {}

	public int getIndex() {
		return index;
	}

	public SCID getScid() {
		return scid;
	}

	public String getName() {
		return name;
	}

	public int getOvertimeSeconds() {
		return overtimeSeconds;
	}

	public float getElixirProductionMultiplier() {
		return elixirProductionMultiplier;
	}

	public float getElixirProductionOvertimeMultiplier() {
		return elixirProductionOvertimeMultiplier;
	}

	public boolean isUseStartingElixir() {
		return useStartingElixir;
	}

	public int getStartingElixir() {
		return startingElixir;
	}

	public boolean isTeam() {
		return team;
	}

	private static boolean initialized = false;
	private static Map<String, GameMode> values = new HashMap<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		Table csv_game_modes = server.getCSVResource("csv_logic/game_modes.csv");
		Column csv_Name = csv_game_modes.getColumn("Name");
		Column csv_OvertimeSeconds = csv_game_modes.getColumn("OvertimeSeconds");
		Column csv_ElixirProductionMultiplier = csv_game_modes.getColumn("ElixirProductionMultiplier");
		Column csv_ElixirProductionOvertimeMultiplier = csv_game_modes.getColumn("ElixirProductionOvertimeMultiplier");
		Column csv_UseStartingElixir = csv_game_modes.getColumn("UseStartingElixir");
		Column csv_StartingElixir = csv_game_modes.getColumn("StartingElixir");
		Column csv_Team = csv_game_modes.getColumn("Team");

		int index = 1;
		for (Row csv_game_mode : csv_game_modes.getRows()) {
			GameMode gameMode = new GameMode();

			gameMode.index = index;
			gameMode.scid = new SCID(72, index);

			gameMode.name = csv_game_mode.getValue(csv_Name).asString();
			gameMode.overtimeSeconds = csv_game_mode.getValue(csv_OvertimeSeconds).asInt();

			Float elixirProductionMultiplier = csv_game_mode.getValue(csv_ElixirProductionMultiplier).asFloatNullable();
			if (elixirProductionMultiplier == null) {
				elixirProductionMultiplier = 100f;
			}
			gameMode.elixirProductionMultiplier = elixirProductionMultiplier / 100f;


			Float elixirProductionOvertimeMultiplier = csv_game_mode.getValue(csv_ElixirProductionOvertimeMultiplier).asFloatNullable();
			if (elixirProductionOvertimeMultiplier == null) {
				elixirProductionOvertimeMultiplier = 100f;
			}
			gameMode.elixirProductionOvertimeMultiplier = elixirProductionOvertimeMultiplier / 100f;

			gameMode.useStartingElixir = csv_game_mode.getValue(csv_UseStartingElixir).asBoolean();
			gameMode.startingElixir = gameMode.useStartingElixir ? csv_game_mode.getValue(csv_StartingElixir).asInt() : 0;
			gameMode.team = csv_game_mode.getValue(csv_Team).asBoolean();

			values.put(gameMode.name, gameMode);
			++index;
		}

		initialized = true;
	}

	public static GameMode by(String name) {
		return values.get(name);
	}
}
