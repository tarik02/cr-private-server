package royaleserver.logic;

import royaleserver.Server;
import royaleserver.assets.Asset;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.database.service.CardService;
import royaleserver.utils.SCID;

import java.util.ArrayList;
import java.util.List;

public class Card {
	public static final int TYPE_CHARACTER = 26;
	public static final int TYPE_BUILDING = 27;
	public static final int TYPE_SPELL = 28;

	private int type;
	private int index;
	private SCID scid;

	private long dbId;

	private String name;
	private Arena unlockArena;
	private Rarity rarity;
	private int elixirCost;
	private boolean notInUse;


	public int getType() {
		return type;
	}

	public int getIndex() { return index; }

	public SCID getScid() {
		return scid;
	}

	public long getDbId() {
		return dbId;
	}

	public String getName() {
		return name;
	}

	public Arena getUnlockArena() {
		return unlockArena;
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

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		int indexCounter = 1;

		indexCounter = loadCards(server, TYPE_CHARACTER, server.getAssetManager().open("csv_logic/spells_characters.csv"), indexCounter);
		indexCounter = loadCards(server, TYPE_BUILDING, server.getAssetManager().open("csv_logic/spells_buildings.csv"), indexCounter);
		indexCounter = loadCards(server, TYPE_SPELL, server.getAssetManager().open("csv_logic/spells_other.csv"), indexCounter);

		initialized = true;
	}

	private static int loadCards(Server server, int type, Asset asset, int indexCounter) throws Server.ServerException {
		CardService cardService = server.getDataManager().getCardService();

		Table csv_cards = asset.csv();
		Column csv_Name = csv_cards.getColumn("Name");
		Column csv_UnlockArena = csv_cards.getColumn("UnlockArena");
		Column csv_Rarity = csv_cards.getColumn("Rarity");
		Column csv_ManaCost = csv_cards.getColumn("ManaCost");
		Column csv_NotInUse = csv_cards.getColumn("NotInUse");

		int cardIndex = 0;

		cardService.beginResolve();
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

			card.dbId = cardService.resolve(card.name).getId();

			cards.add(card);
			++indexCounter;
		}
		cardService.endResolve();

		return indexCounter;
	}

	public static Card by(String name) {
		for (Card card : cards) {
			if (card.getName().equals(name)) {
				return card;
			}
		}

		return null;
	}

	public static Card byDB(long id) {
		for (Card card : cards) {
			if (card.getDbId() == id) {
				return card;
			}
		}

		return null;
	}
}
