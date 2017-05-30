package royaleserver.logic;

import royaleserver.Server;
import royaleserver.csv.Column;
import royaleserver.csv.Row;
import royaleserver.csv.Table;
import royaleserver.database.service.ClanRoleService;

import java.util.ArrayList;
import java.util.List;

public class ClanRole {
	private long dbId;
	private int index;
	private String name;

	private int level;
	private boolean canInvite, canSendMail, canChangeClanSettings, canAcceptJoinRequest, canKick, canBePromotedToLeader, canPromoteToOwnLevel;

	private ClanRole() {}

	public long getDbId() {
		return dbId;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}

	public int getLevel() {
		return level;
	}

	public boolean isCanInvite() {
		return canInvite;
	}

	public boolean isCanSendMail() {
		return canSendMail;
	}

	public boolean isCanChangeClanSettings() {
		return canChangeClanSettings;
	}

	public boolean isCanAcceptJoinRequest() {
		return canAcceptJoinRequest;
	}

	public boolean isCanKick() {
		return canKick;
	}

	public boolean isCanBePromotedToLeader() {
		return canBePromotedToLeader;
	}

	public boolean isCanPromoteToOwnLevel() {
		return canPromoteToOwnLevel;
	}

	private static boolean initialized = false;
	private static List<ClanRole> values = new ArrayList<>();

	public static void init(Server server) throws Server.ServerException {
		if (initialized) {
			return;
		}

		ClanRoleService clanRoleService = server.getDataManager().getClanRoleService();

		Table csv_roles = server.getAssetManager().open("csv_logic/alliance_roles.csv").csv();
		Column csv_Name = csv_roles.getColumn("Name");
		Column csv_Level = csv_roles.getColumn("Level");
		Column csv_CanInvite = csv_roles.getColumn("CanInvite");
		Column csv_CanSendMail = csv_roles.getColumn("CanSendMail");
		Column csv_CanChangeAllianceSettings = csv_roles.getColumn("CanChangeAllianceSettings");
		Column csv_CanAcceptJoinRequest = csv_roles.getColumn("CanAcceptJoinRequest");
		Column csv_CanKick = csv_roles.getColumn("CanKick");
		Column csv_CanBePromotedToLeader = csv_roles.getColumn("CanBePromotedToLeader");
		Column csv_CanPromoteToOwnLevel = csv_roles.getColumn("CanPromoteToOwnLevel");

		clanRoleService.beginResolve();
		int i = 0;
		for (Row csv_role : csv_roles.getRows()) {
			ClanRole role = new ClanRole();

			role.index = i++;
			role.name = csv_role.getValue(csv_Name).asString();
			role.level = csv_role.getValue(csv_Level).asInt();
			role.canInvite = csv_role.getValue(csv_CanInvite).asBoolean();
			role.canSendMail = csv_role.getValue(csv_CanSendMail).asBoolean();
			role.canChangeClanSettings = csv_role.getValue(csv_CanChangeAllianceSettings).asBoolean();
			role.canAcceptJoinRequest = csv_role.getValue(csv_CanAcceptJoinRequest).asBoolean();
			role.canKick = csv_role.getValue(csv_CanKick).asBoolean();
			role.canBePromotedToLeader = csv_role.getValue(csv_CanBePromotedToLeader).asBoolean();
			role.canPromoteToOwnLevel = csv_role.getValue(csv_CanPromoteToOwnLevel).asBoolean();

			role.dbId = clanRoleService.resolve(role.name).getId();

			values.add(role);
		}
		clanRoleService.endResolve();

		initialized = true;
	}

	public static ClanRole by(String name) {
		for (ClanRole Role : values) {
			if (Role.name.equals(name)) {
				return Role;
			}
		}

		return null;
	}

	public static ClanRole by(int index) {
		for (ClanRole Role : values) {
			if (Role.index == index) {
				return Role;
			}
		}

		return null;
	}

	public static ClanRole byDB(long id) {
		for (ClanRole Role : values) {
			if (Role.dbId == id) {
				return Role;
			}
		}

		return null;
	}
}
