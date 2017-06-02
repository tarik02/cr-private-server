package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.utils.SCID;

import java.util.ArrayList;
import java.util.List;

public final class ClanBadge extends DBLogic {
	public static final int SCID_HIGH = 16;

	private SCID scid;

	private ClanBadge() {}

	public SCID getScid() {
		return scid;
	}


	private static boolean initialized = false;
	private static List<ClanBadge> values = new ArrayList<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		Table csv_badges = server.getAssetManager().open("csv_logic/alliance_badges.csv").csv();
		Column csv_Name = csv_badges.getColumn("Name");

		int i = 0;
		for (Row csv_badge : csv_badges.getRows()) {
			ClanBadge badge = new ClanBadge();

			badge.scid = new SCID(SCID_HIGH, i++);
			badge.name = csv_badge.getValue(csv_Name).asString();

			values.add(badge);
		}

		init(values, server.getDataManager().getClanBadgeService());

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
