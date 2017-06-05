package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.utils.SCID;

import java.util.ArrayList;
import java.util.List;

public final class Chest extends DBLogic implements Cloneable {
	public static final int SCID_HIGH = 19;

	private int index;
	private SCID scid;

	private Arena arena;
	private boolean inShop, inArenaInfo;
	private int timeTakenDays, timeTakesHours, timeTakenMinutes, timeTakenSeconds;
	private int randomSpells, differentSpells;
	private int rareChance, epicChance, legendaryChance;
	private Card[] guaranteedSpells;
	private int minGoldPerCard, maxGoldPerCard;

	private Chest() {}

	public int getIndex() {
		return index;
	}

	public SCID getScid() {
		return scid;
	}

	public Arena getArena() {
		return arena;
	}

	public boolean isInShop() {
		return inShop;
	}

	public boolean isInArenaInfo() {
		return inArenaInfo;
	}

	public int getTimeTakenDays() {
		return timeTakenDays;
	}

	public int getTimeTakesHours() {
		return timeTakesHours;
	}

	public int getTimeTakenMinutes() {
		return timeTakenMinutes;
	}

	public int getTimeTakenSeconds() {
		return timeTakenSeconds;
	}

	public int getRandomSpells() {
		return randomSpells;
	}

	public int getDifferentSpells() {
		return differentSpells;
	}

	public int getRareChance() {
		return rareChance;
	}

	public int getEpicChance() {
		return epicChance;
	}

	public int getLegendaryChance() {
		return legendaryChance;
	}

	public Card[] getGuaranteedSpells() {
		return guaranteedSpells;
	}

	public int getMinGoldPerCard() {
		return minGoldPerCard;
	}

	public int getMaxGoldPerCard() {
		return maxGoldPerCard;
	}

	public int getOpenTicks() {
		return (((timeTakenDays * 24 + timeTakesHours) * 60 + timeTakenMinutes) * 60 + timeTakenSeconds) * 20;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private static boolean initialized = false;
	private static List<Chest> values = new ArrayList<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		Table csv_chests = server.getAssetManager().open("csv_logic/treasure_chests.csv").csv();
		Column csv_Name = csv_chests.getColumn("Name");
		Column csv_BaseChest = csv_chests.getColumn("BaseChest");
		Column csv_Arena = csv_chests.getColumn("Arena");
		Column csv_InShop = csv_chests.getColumn("InShop");
		Column csv_InArenaInfo = csv_chests.getColumn("InArenaInfo");
		Column csv_TimeTakenDays = csv_chests.getColumn("TimeTakenDays");
		Column csv_TimeTakenHours = csv_chests.getColumn("TimeTakenHours");
		Column csv_TimeTakenMinutes = csv_chests.getColumn("TimeTakenMinutes");
		Column csv_TimeTakenSeconds = csv_chests.getColumn("TimeTakenSeconds");
		Column csv_RandomSpells = csv_chests.getColumn("RandomSpells");
		Column csv_DifferentSpells = csv_chests.getColumn("DifferentSpells");
		Column csv_RareChance = csv_chests.getColumn("RareChance");
		Column csv_EpicChance = csv_chests.getColumn("EpicChance");
		Column csv_LegendaryChance = csv_chests.getColumn("LegendaryChance");
		Column csv_GuaranteedSpells = csv_chests.getColumn("GuaranteedSpells");
		Column csv_MinGoldPerCard = csv_chests.getColumn("MinGoldPerCard");
		Column csv_MaxGoldPerCard = csv_chests.getColumn("MaxGoldPerCard");

		int i = 0;
		for (Row csv_chest : csv_chests.getRows()) {
			Chest chest;

			if (csv_BaseChest.getValue(csv_chest).empty()) {
				chest = new Chest();
			} else {
				String baseChestName = csv_chest.getValue(csv_BaseChest).asString();
				chest = by(baseChestName);

				if (chest == null) {
					//throw new Server.ServerException("Base chest '" + baseChestName + "' is not found for chest '" + csv_chest.getValue(csv_Name).asString() + "'.");
					chest = new Chest();
				} else {
					try {
						chest = (Chest)chest.clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}

			chest.index = i;
			chest.scid = new SCID(SCID_HIGH, i);

			chest.name = csv_chest.getValue(csv_Name).asString();
			chest.arena = Arena.by(csv_chest.getValue(csv_Arena).asString(chest.arena == null ? "" : chest.arena.getName()));
			chest.inShop = csv_chest.getValue(csv_InShop).asBoolean(chest.inShop);
			chest.inArenaInfo = csv_chest.getValue(csv_InArenaInfo).asBoolean(chest.inArenaInfo);
			chest.timeTakenDays = csv_chest.getValue(csv_TimeTakenDays).asInt(chest.timeTakenDays);
			chest.timeTakesHours = csv_chest.getValue(csv_TimeTakenHours).asInt(chest.timeTakesHours);
			chest.timeTakenMinutes = csv_chest.getValue(csv_TimeTakenMinutes).asInt(chest.timeTakenMinutes);
			chest.timeTakenSeconds = csv_chest.getValue(csv_TimeTakenSeconds).asInt(chest.timeTakenSeconds);
			chest.randomSpells = csv_chest.getValue(csv_RandomSpells).asInt(chest.randomSpells);
			chest.differentSpells = csv_chest.getValue(csv_DifferentSpells).asInt(chest.differentSpells);
			chest.rareChance = csv_chest.getValue(csv_RareChance).asInt(chest.rareChance);
			chest.epicChance = csv_chest.getValue(csv_EpicChance).asInt(chest.epicChance);
			chest.legendaryChance = csv_chest.getValue(csv_LegendaryChance).asInt(chest.legendaryChance);

			String[] guaranteedSpells = csv_chest.getValue(csv_GuaranteedSpells).asStringArray();
			chest.guaranteedSpells = new Card[guaranteedSpells.length];
			for (int j = 0; j < guaranteedSpells.length; ++j) {
				chest.guaranteedSpells[j] = Card.by(guaranteedSpells[j]);
			}

			chest.minGoldPerCard = csv_chest.getValue(csv_MinGoldPerCard).asInt(chest.minGoldPerCard);
			chest.maxGoldPerCard = csv_chest.getValue(csv_MaxGoldPerCard).asInt(chest.maxGoldPerCard);

			values.add(chest);
			++i;
		}

		init(values, server.getDataManager().getChestService());

		initialized = true;
	}

	public static Chest by(String name) {
		for (Chest chest : values) {
			if (chest.name.equals(name)) {
				return chest;
			}
		}

		return null;
	}

	public static Chest by(int index) {
		for (Chest chest : values) {
			if (chest.index == index) {
				return chest;
			}
		}

		return null;
	}

	public static Chest byDB(long id) {
		for (Chest chest : values) {
			if (chest.dbId == id) {
				return chest;
			}
		}

		return null;
	}
}
