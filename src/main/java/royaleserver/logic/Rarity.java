package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;

import java.util.ArrayList;
import java.util.List;

public class Rarity {
	private String name;
	private int levelCount;
	private int donateCapacity, sortCapacity, donateReward, donateXP;
	private int goldConversionValue, chanceWeight;
	private int[] upgradeExp, upgradeMaterialCount, upgradeCost;
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

	public int getSortCapacity() {
		return sortCapacity;
	}

	public int getDonateReward() {
		return donateReward;
	}

	public int getDonateXP() {
		return donateXP;
	}

	public int getGoldConversionValue() {
		return goldConversionValue;
	}

	public int getChanceWeight() {
		return chanceWeight;
	}

	public int[] getUpgradeExp() {
		return upgradeExp;
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
	private static List<Rarity> values = new ArrayList<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		Table csv_rarities = server.getAssetManager().open("csv_logic/rarities.csv").csv();
		Column csv_Name = csv_rarities.getColumn("Name");
		Column csv_LevelCount = csv_rarities.getColumn("LevelCount");
		Column csv_DonateCapacity = csv_rarities.getColumn("DonateCapacity");
		Column csv_SortCapacity = csv_rarities.getColumn("SortCapacity");
		Column csv_DonateReward = csv_rarities.getColumn("DonateReward");
		Column csv_DonateXP = csv_rarities.getColumn("DonateXP");
		Column csv_GoldConversionValue = csv_rarities.getColumn("GoldConversionValue");
		Column csv_ChanceWeight = csv_rarities.getColumn("ChanceWeight");
		Column csv_UpgradeExp = csv_rarities.getColumn("UpgradeExp");
		Column csv_UpgradeMaterialCount = csv_rarities.getColumn("UpgradeMaterialCount");
		Column csv_UpgradeCost = csv_rarities.getColumn("UpgradeCost");
		Column csv_PowerLevelMultiplier = csv_rarities.getColumn("PowerLevelMultiplier");

		for (Row csv_rarity : csv_rarities.getRows()) {
			Rarity rarity = new Rarity();

			rarity.name = csv_rarity.getValue(csv_Name).asString();
			rarity.levelCount = csv_rarity.getValue(csv_LevelCount).asInt();
			rarity.donateCapacity = csv_rarity.getValue(csv_DonateCapacity).asInt();
			rarity.sortCapacity = csv_rarity.getValue(csv_SortCapacity).asInt();
			rarity.donateReward = csv_rarity.getValue(csv_DonateReward).asInt();
			rarity.donateXP = csv_rarity.getValue(csv_DonateXP).asInt();
			rarity.goldConversionValue = csv_rarity.getValue(csv_GoldConversionValue).asInt();
			rarity.chanceWeight = csv_rarity.getValue(csv_ChanceWeight).asInt();
			rarity.upgradeExp = csv_rarity.getValue(csv_UpgradeExp).asIntArray();
			rarity.upgradeMaterialCount = csv_rarity.getValue(csv_UpgradeMaterialCount).asIntArray();
			rarity.upgradeCost = csv_rarity.getValue(csv_UpgradeCost).asIntArray();
			rarity.powerLevelMultiplier = csv_rarity.getValue(csv_PowerLevelMultiplier).asIntArray();

			values.add(rarity);
		}

		initialized = true;
	}

	public static Rarity[] all() {
		return values.toArray(new Rarity[0]);
	}

	public static Rarity by(String name) {
		for (Rarity rarity : values) {
			if (rarity.name.equalsIgnoreCase(name)) {
				return rarity;
			}
		}

		return null;
	}
}
