package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.database.service.ClanBadgeService;
import royaleserver.utils.SCID;

import java.util.ArrayList;
import java.util.List;

public class ClanBadge {
	public static final int SCID_HIGH = 16;

	private long dbId;
	private SCID scid;
	private String name;

	public long getDbId() {
		return dbId;
	}

	public SCID getScid() {
		return scid;
	}

	public String getName() {
		return name;
	}


	private static boolean initialized = false;
	private static List<ClanBadge> values = new ArrayList<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		ClanBadgeService clanBadgeService = server.getDataManager().getClanBadgeService();

		Table csv_badges = server.getAssetManager().open("csv_logic/alliance_badges.csv").csv();
		Column csv_Name = csv_badges.getColumn("Name");

		clanBadgeService.beginResolve();
		int i = 0;
		for (Row csv_chest : csv_badges.getRows()) {
			ClanBadge badge = new ClanBadge();

			badge.scid = new SCID(SCID_HIGH, i++);
			badge.name = csv_chest.getValue(csv_Name).asString();
			badge.dbId = clanBadgeService.resolve(badge.name).getId();

			values.add(badge);
		}
		clanBadgeService.endResolve();

		initialized = true;
	}

	public static ClanBadge by(String name) {
		for (ClanBadge badge : values) {
			if (badge.name.equals(name)) {
				return badge;
			}
		}

		return null;
	}

	public static ClanBadge by(SCID scid) {
		for (ClanBadge badge : values) {
			if (badge.scid.equals(scid)) {
				return badge;
			}
		}

		return null;
	}

	public static ClanBadge byDB(long id) {
		for (ClanBadge badge : values) {
			if (badge.dbId == id) {
				return badge;
			}
		}

		return null;
	}
}
