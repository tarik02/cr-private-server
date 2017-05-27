package royaleserver.database.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
@NamedQueries({
		@NamedQuery(name = ".getAll", query = "SELECT c from PlayerEntity c"),
		@NamedQuery(name = ".clear", query = "DELETE FROM PlayerEntity p")
})
public class PlayerEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(length = 32, unique = true, nullable = true)
	private String name;

	@Column(nullable = false)
	private int gold;

	@Column(nullable = false)
	private int gems;

	@Column(length = 64, nullable = false)
	private String passToken; // Account security, like password

	@Column(nullable = false)
	@OneToMany(mappedBy = "player")
	private Set<PlayerCardEntity> cards = new HashSet<>();


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

	public String getPassToken() {
		return passToken;
	}

	public PlayerEntity setPassToken(String passToken) {
		this.passToken = passToken;
		return this;
	}

	public Set<PlayerCardEntity> getCards() {
		return cards;
	}

	public PlayerEntity setCards(Set<PlayerCardEntity> cards) {
		this.cards = cards;
		return this;
	}
}
