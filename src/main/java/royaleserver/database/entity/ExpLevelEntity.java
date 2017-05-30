package royaleserver.database.entity;

import javax.persistence.*;

@Entity
@Table(name = "exp_levels")
@NamedQuery(name = "ExpLevelEntity.getByName", query = "SELECT c FROM ExpLevelEntity c WHERE c.name = :name")
public class ExpLevelEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private int name;


	public long getId() {
		return id;
	}

	public ExpLevelEntity setId(long id) {
		this.id = id;
		return this;
	}

	public int getName() {
		return name;
	}

	public ExpLevelEntity setName(int name) {
		this.name = name;
		return this;
	}
}
