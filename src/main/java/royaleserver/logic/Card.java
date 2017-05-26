package royaleserver.logic;

import royaleserver.Server;
import royaleserver.assets.Asset;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.database.entity.AssetEntity;
import royaleserver.database.service.AssetService;
import royaleserver.utils.SCID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card {
	public static final int TYPE_CHARACTER = 26;
	public static final int TYPE_BUILDING = 27;
	public static final int TYPE_SPELL = 28;

	private int type;
	private int index;
	private SCID scid;

	private String name;
	private Arena unlockArena;
	private Rarity rarity;
	private int elixirCost;
	private boolean notInUse;


	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public int getIndex() { return index; }

	public SCID getScid() {
		return scid;
	}

	public Rarity getRarity() {
		return rarity;
	}

	public int getElixirCost() {
		return elixirCost;
	}

	public boolean isNotInUse() {
		return notInUse;
	}

	private static boolean initialized = false;
	private static List<Card> cards = new ArrayList<>();
	private static Map<Integer, Map<String, Card>> values = new HashMap<>();
	private static boolean needUpdate = false;

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		int indexCounter = 1;

		indexCounter = loadCards(server, TYPE_CHARACTER, server.getAssetManager().open("csv_logic/spells_characters.csv"), indexCounter);
		indexCounter = loadCards(server, TYPE_BUILDING, server.getAssetManager().open("csv_logic/spells_buildings.csv"), indexCounter);
		indexCounter = loadCards(server, TYPE_SPELL, server.getAssetManager().open("csv_logic/spells_other.csv"), indexCounter);

		initialized = true;

		if (needUpdate) {
			needUpdate = false;
			server.getDataManager().getCardService().update();
		}
	}

	private static int loadCards(Server server, int type, Asset asset, int indexCounter) throws Server.ServerException {
		Table csv_cards = asset.csv();
		Column csv_Name = csv_cards.getColumn("Name");
		Column csv_UnlockArena = csv_cards.getColumn("UnlockArena");
		Column csv_Rarity = csv_cards.getColumn("Rarity");
		Column csv_ManaCost = csv_cards.getColumn("ManaCost");
		Column csv_NotInUse = csv_cards.getColumn("NotInUse");

		Map<String, Card> cards = new HashMap<>();
		values.put(type, cards);

		int cardIndex = 0;
		for (Row csv_card : csv_cards.getRows()) {
			Card card = new Card();

			card.type = type;
			card.index = indexCounter;
			card.scid = new SCID(type, cardIndex++);

			card.name = csv_Name.getValue(csv_card).asString();
			card.unlockArena = Arena.by(csv_UnlockArena.getValue(csv_card).asString());
			card.rarity = Rarity.by(csv_Rarity.getValue(csv_card).asString());
			card.elixirCost = csv_ManaCost.getValue(csv_card).asInt();
			card.notInUse = csv_NotInUse.getValue(csv_card).asBoolean();

			cards.put(card.name, card);
			Card.cards.add(card);
			++indexCounter;
		}

		AssetService assetService = server.getDataManager().getAssetService();
		AssetEntity assetEntity = assetService.get(asset.name());
		if (asset.isNewerThan(assetEntity)) {
			needUpdate = true;

			assetService.update(assetEntity);
		}

		return indexCounter;
	}

	public static List<Card> cards() {
		return cards;
	}

	public static Card by(String name) {
		for (Map<String, Card> type : values.values()) {
			if (type.containsKey(name)) {
				return type.get(name);
			}
		}

		return null;
	}
}
