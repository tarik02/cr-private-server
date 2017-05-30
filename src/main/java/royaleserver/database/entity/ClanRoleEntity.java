package royaleserver.database.entity;

import javax.persistence.*;

@Entity
@Table(name = "clan_roles")
@NamedQuery(name = "ClanRoleEntity.getByName", query = "SELECT c FROM ClanRoleEntity c WHERE c.name = :name")
public class ClanRoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String name;


	public long getId() {
		return id;
	}

	public ClanRoleEntity setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ClanRoleEntity setName(String name) {
		this.name = name;
		return this;
	}
}
