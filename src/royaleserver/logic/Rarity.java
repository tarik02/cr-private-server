package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;

import java.util.HashMap;
import java.util.Map;

public class Rarity {
	private String name;
	private int levelCount;
	private int donateCapacity, donateReward, donateXP;
	private int[] upgrageExp, upgradeMaterialCount, upgradeCost;
	private int[] powerLevelMultiplier;

	private Rarity() {}

	public String getName() {
		return name;
	}

	public int getLevelCount() {
		return levelCount;
	}

	public int getDonateCapacity() {
		return donateCapacity;
	}

	public int getDonateReward() {
		return donateReward;
	}

	public int getDonateXP() {
		return donateXP;
	}

	public int[] getUpgrageExp() {
		return upgrageExp;
	}

	public int[] getUpgradeMaterialCount() {
		return upgradeMaterialCount;
	}

	public int[] getUpgradeCost() {
		return upgradeCost;
	}

	public int[] getPowerLevelMultiplier() {
		return powerLevelMultiplier;
	}

	private static boolean initialized = false;
	private static Map<String, Rarity> values = new HashMap<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		Table csv_rarities = server.getCSVResource("csv_logic/rarities.csv");
		Column csv_Name = csv_rarities.getColumn("Name");
		Column csv_LevelCount = csv_rarities.getColumn("LevelCount");
		Column csv_DonateCapacity = csv_rarities.getColumn("DonateCapacity");
		Column csv_DonateReward = csv_rarities.getColumn("DonateReward");
		Column csv_DonateXP = csv_rarities.getColumn("DonateXP");
		Column csv_UpgradeExp = csv_rarities.getColumn("UpgradeExp");
		Column csv_UpgradeMaterialCount = csv_rarities.getColumn("UpgradeMaterialCount");
		Column csv_UpgradeCost = csv_rarities.getColumn("UpgradeCost");
		Column csv_PowerLevelMultiplier = csv_rarities.getColumn("PowerLevelMultiplier");

		for (Row csv_rarity : csv_rarities.getRows()) {
			Rarity rarity = new Rarity();

			rarity.name = csv_rarity.getValue(csv_Name).asString();
			rarity.levelCount = csv_rarity.getValue(csv_LevelCount).asInt();
			rarity.donateCapacity = csv_rarity.getValue(csv_DonateCapacity).asInt();
			rarity.donateReward = csv_rarity.getValue(csv_DonateReward).asInt();
			rarity.donateXP = csv_rarity.getValue(csv_DonateXP).asInt();
			rarity.upgrageExp = csv_rarity.getValue(csv_UpgradeExp).asIntArray();
			rarity.upgradeMaterialCount = csv_rarity.getValue(csv_UpgradeMaterialCount).asIntArray();
			rarity.upgradeCost = csv_rarity.getValue(csv_UpgradeCost).asIntArray();
			rarity.powerLevelMultiplier = csv_rarity.getValue(csv_PowerLevelMultiplier).asIntArray();

			values.put(rarity.name, rarity);
		}

		initialized = true;
	}

	public static Rarity by(String name) {
		return values.get(name);
	}
}
