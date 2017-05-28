package royaleserver.database.entity;

import javax.persistence.*;

@Entity
@Table(name = "chests")
@NamedQuery(name = "ChestEntity.getByName", query = "SELECT c FROM ChestEntity c WHERE c.name = :name")
public class ChestEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String name;


	public long getId() {
		return id;
	}

	public ChestEntity setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ChestEntity setName(String name) {
		this.name = name;
		return this;
	}
}
