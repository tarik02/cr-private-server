package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.utils.SCID;

import java.util.ArrayList;
import java.util.List;

public final class Arena extends DBLogic {
	public static final int SCID_HIGH = 0; // TODO: Get it

	private int index;
	private SCID scid;
	private int arena;
	private String chestArena;
	private boolean isInUse;
	private int trophyLimit, demoteTrophyLimit;
	private Integer seasonTrophyRequest;
	private int chestRewardMultiplier, chestShopPriceMultiplier;
	private int requestSize;
	private int maxDonationCountCommon, maxDonationCountRare, maxDonationCountEpic;
	private int dailyDonationCapacityLimit;
	private int battleRewardGold;
	private String releaseDate;

	private Arena() {}

	public int getIndex() {
		return index;
	}

	public SCID getScid() {
		return scid;
	}

	public int getArena() {
		return arena;
	}

	public String getChestArena() {
		return chestArena;
	}

	public boolean isInUse() {
		return isInUse;
	}

	public int getTrophyLimit() {
		return trophyLimit;
	}

	public int getDemoteTrophyLimit() {
		return demoteTrophyLimit;
	}

	public Integer getSeasonTrophyRequest() {
		return seasonTrophyRequest;
	}

	public int getChestRewardMultiplier() {
		return chestRewardMultiplier;
	}

	public int getChestShopPriceMultiplier() {
		return chestShopPriceMultiplier;
	}

	public int getRequestSize() {
		return requestSize;
	}

	public int getMaxDonationCountCommon() {
		return maxDonationCountCommon;
	}

	public int getMaxDonationCountRare() {
		return maxDonationCountRare;
	}

	public int getMaxDonationCountEpic() {
		return maxDonationCountEpic;
	}

	public int getDailyDonationCapacityLimit() {
		return dailyDonationCapacityLimit;
	}

	public int getBattleRewardGold() {
		return battleRewardGold;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	private static boolean initialized = false;
	private static List<Arena> values = new ArrayList<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		Table csv_arenas = server.getAssetManager().open("csv_logic/arenas.csv").csv();
		Column csv_Name = csv_arenas.getColumn("Name");
		Column csv_Arena = csv_arenas.getColumn("Arena");
		Column csv_ChestArena = csv_arenas.getColumn("ChestArena");
		Column csv_IsInUse = csv_arenas.getColumn("IsInUse");
		Column csv_TrophyLimit = csv_arenas.getColumn("TrophyLimit");
		Column csv_DemoteTrophyLimit = csv_arenas.getColumn("DemoteTrophyLimit");
		Column csv_SeasonTrophyReset = csv_arenas.getColumn("SeasonTrophyReset");
		Column csv_ChestRewardMultiplier = csv_arenas.getColumn("ChestRewardMultiplier");
		Column csv_ChestShopPriceMultiplier = csv_arenas.getColumn("ChestShopPriceMultiplier");
		Column csv_RequestSize = csv_arenas.getColumn("RequestSize");
		Column csv_MaxDonationCountCommon = csv_arenas.getColumn("MaxDonationCountCommon");
		Column csv_MaxDonationCountRare = csv_arenas.getColumn("MaxDonationCountRare");
		Column csv_MaxDonationCountEpic = csv_arenas.getColumn("MaxDonationCountEpic");
		Column csv_PvpLocation = csv_arenas.getColumn("PvpLocation");
		Column csv_DailyDonationCapacityLimit = csv_arenas.getColumn("DailyDonationCapacityLimit");
		Column csv_BattleRewardGold = csv_arenas.getColumn("BattleRewardGold");
		Column csv_ReleaseDate = csv_arenas.getColumn("ReleaseDate");
		Column csv_SeasonRewardChest = csv_arenas.getColumn("SeasonRewardChest");

		int i = 0;
		for (Row csv_arena : csv_arenas.getRows()) {
			Arena arena = new Arena();

			arena.index = i;
			arena.scid = new SCID(SCID_HIGH, i);
			arena.name = csv_arena.getValue(csv_Name).asString();
			arena.arena = csv_arena.getValue(csv_Arena).asInt();
			arena.chestArena = csv_arena.getValue(csv_ChestArena).asString();
			arena.isInUse = csv_arena.getValue(csv_IsInUse).asBoolean();
			arena.trophyLimit = csv_arena.getValue(csv_TrophyLimit).asInt();
			arena.demoteTrophyLimit = csv_arena.getValue(csv_DemoteTrophyLimit).asInt();
			arena.seasonTrophyRequest = csv_arena.getValue(csv_SeasonTrophyReset).asIntNullable();
			arena.chestRewardMultiplier = csv_arena.getValue(csv_ChestRewardMultiplier).asInt();
			arena.chestShopPriceMultiplier = csv_arena.getValue(csv_ChestShopPriceMultiplier).asInt();
			arena.requestSize = csv_arena.getValue(csv_RequestSize).asInt();
			arena.maxDonationCountCommon = csv_arena.getValue(csv_MaxDonationCountCommon).asInt();
			arena.maxDonationCountRare = csv_arena.getValue(csv_MaxDonationCountRare).asInt();
			arena.maxDonationCountEpic = csv_arena.getValue(csv_MaxDonationCountEpic).asInt();
			arena.dailyDonationCapacityLimit = csv_arena.getValue(csv_DailyDonationCapacityLimit).asInt();
			arena.battleRewardGold = csv_arena.getValue(csv_BattleRewardGold).asInt();
			arena.releaseDate = csv_arena.getValue(csv_ReleaseDate).asString(true);

			values.add(arena);
			++i;
		}

		init(values, server.getDataManager().getArenaService());

		initialized = true;
	}

	public static Arena by(String name) {
		for (Arena arena : values) {
			if (arena.name.equalsIgnoreCase(name)) {
				return arena;
			}
		}

		return null;
	}

	public static Arena byDB(long id) {
		for (Arena arena : values) {
			if (arena.dbId == id) {
				return arena;
			}
		}

		return null;
	}
}
