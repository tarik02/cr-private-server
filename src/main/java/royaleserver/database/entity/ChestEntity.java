package royaleserver.database.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "chests")
@NamedQuery(name = "ChestEntity.all", query = "SELECT chestEntity FROM ChestEntity chestEntity")
public class ChestEntity extends LogicEntity<ChestEntity> {
	public ChestEntity() {}

	public ChestEntity(String name) {
		super(name);
	}
}
