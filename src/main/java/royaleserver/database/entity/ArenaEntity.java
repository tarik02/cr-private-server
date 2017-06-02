package royaleserver.database.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "arenas")
@NamedQuery(name = "ArenaEntity.all", query = "SELECT arenaEntity FROM ArenaEntity arenaEntity")
public class ArenaEntity extends LogicEntity<ArenaEntity> {
	public ArenaEntity() {}

	public ArenaEntity(String name) {
		super(name);
	}
}
