package royaleserver.database.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "clan_badges")
@NamedQuery(name = "ClanBadgeEntity.all", query = "SELECT clanBadgeEntity FROM ClanBadgeEntity clanBadgeEntity")
public class ClanBadgeEntity extends LogicEntity<ClanBadgeEntity> {
	public ClanBadgeEntity() {}

	public ClanBadgeEntity(String name) {
		super(name);
	}
}
