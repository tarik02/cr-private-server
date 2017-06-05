package royaleserver.database.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "clan_roles")
@NamedQuery(name = "ClanRoleEntity.all", query = "SELECT clanRoleEntity FROM ClanRoleEntity clanRoleEntity")
public class ClanRoleEntity extends LogicEntity<ClanRoleEntity> {
	public ClanRoleEntity() {}

	public ClanRoleEntity(String name) {
		super(name);
	}
}
