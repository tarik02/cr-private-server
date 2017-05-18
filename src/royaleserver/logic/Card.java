package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;

import java.util.HashMap;
import java.util.Map;

public class Card {
	public static final int TYPE_CHARACTER = 26;
	public static final int TYPE_BUILDING = 27;
	public static final int TYPE_SPELL = 28;

	private int type;
	private int id;
	private String name;
	//private Arena unlockArena;
	private Rarity rarity;
	private int elixir;
	private boolean notInUse;


	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public int getElixir() {
		return elixir;
	}

	public boolean isNotInUse() {
		return notInUse;
	}

	private static boolean initialized = false;
	private static Map<Integer, Map<String, Card>> values = new HashMap<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		loadCards(TYPE_CHARACTER, server.getCSVResource("csv_logic/spells_characters.csv"));
		loadCards(TYPE_BUILDING, server.getCSVResource("csv_logic/spells_buildings.csv"));
		loadCards(TYPE_SPELL, server.getCSVResource("csv_logic/spells_other.csv"));

		initialized = true;
	}

	private static void loadCards(int type, Table csv_cards) throws Server.ServerException {
		Column csv_Name = csv_cards.getColumn("Name");
		Column csv_UnlockArena = csv_cards.getColumn("UnlockArena");
		Column csv_Rarity = csv_cards.getColumn("Rarity");
		Column csv_ManaCost = csv_cards.getColumn("ManaCost");
		Column csv_NotInUse = csv_cards.getColumn("NotInUse");

		for (Row csv_card : csv_cards.getRows()) {

		}
	}

	public Card by(String name) {
		for (Map<String, Card> type : values.values()) {
			if (type.containsKey(name)) {
				return type.get(name);
			}
		}

		return null;
	}
}
