package royaleserver.database.entity;

import javax.persistence.*;

@Entity
@Table(name = "players")
@NamedQueries({
		@NamedQuery(name = ".getAll", query = "SELECT c from PlayerEntity c"),
		@NamedQuery(name = ".clear", query = "DELETE FROM PlayerEntity p")
})
public class PlayerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 32, unique = true, nullable = true)
	private String name;

	@Column(nullable = false)
	private int gold;

	@Column(nullable = false)
	private int gems;

	public long getId() {
		return id;
	}

	public PlayerEntity setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public PlayerEntity setName(String name) {
		this.name = name;
		return this;
	}

	public int getGold() {
		return gold;
	}

	public PlayerEntity setGold(int gold) {
		this.gold = gold;
		return this;
	}

	public int getGems() {
		return gems;
	}

	public PlayerEntity setGems(int gems) {
		this.gems = gems;
		return this;
	}
}
