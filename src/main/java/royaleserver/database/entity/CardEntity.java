package royaleserver.database.entity;

import javax.persistence.*;

@Entity
@Table(name = "cards")
@NamedQuery(name = "getByName", query = "SELECT c FROM CardEntity c WHERE c.name = :name")
public class CardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String name;


	public long getId() {
		return id;
	}

	public CardEntity setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public CardEntity setName(String name) {
		this.name = name;
		return this;
	}
}
