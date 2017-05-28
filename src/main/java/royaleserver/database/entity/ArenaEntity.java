package royaleserver.database.entity;

import javax.persistence.*;

@Entity
@Table(name = "arenas")
@NamedQuery(name = "ArenaEntity.getByName", query = "SELECT c FROM ArenaEntity c WHERE c.name = :name")
public class ArenaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String name;


	public long getId() {
		return id;
	}

	public ArenaEntity setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ArenaEntity setName(String name) {
		this.name = name;
		return this;
	}
}
