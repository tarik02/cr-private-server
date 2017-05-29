package royaleserver.database.entity;

import javax.persistence.*;

@Entity
@Table(name = "clan_badges")
@NamedQuery(name = "ClanBadgeEntity.getByName", query = "SELECT c FROM ClanBadgeEntity c WHERE c.name = :name")
public class ClanBadgeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String name;


	public long getId() {
		return id;
	}

	public ClanBadgeEntity setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ClanBadgeEntity setName(String name) {
		this.name = name;
		return this;
	}
}
