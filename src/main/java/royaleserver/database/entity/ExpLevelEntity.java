package royaleserver.database.entity;

import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "exp_levels")
@NamedQuery(name = "ExpLevelEntity.all", query = "SELECT expLevelEntity FROM ExpLevelEntity expLevelEntity")
public class ExpLevelEntity extends LogicEntity<ExpLevelEntity> {
	public ExpLevelEntity() {}

	public ExpLevelEntity(String name) {
		super(name);
	}
}
