package royaleserver.database.entity;

import royaleserver.utils.SCID;

import javax.persistence.*;

@Entity
@Table(name = "cards", indexes = {
		@Index(name = "type_index", columnList = "type,card_index")
})
public class CardEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private int type;

	@Column(name = "card_index", nullable = false)
	private long index;

	@Column(nullable = false, unique = true)
	private SCID scid;

	@Column(nullable = false, unique = true)
	private String name;


	public long getId() {
		return id;
	}

	public CardEntity setId(long id) {
		this.id = id;
		return this;
	}

	public int getType() {
		return type;
	}

	public CardEntity setType(int type) {
		this.type = type;
		return this;
	}

	public long getIndex() {
		return index;
	}

	public CardEntity setIndex(long index) {
		this.index = index;
		return this;
	}

	public SCID getScid() {
		return scid;
	}

	public CardEntity setScid(SCID scid) {
		this.scid = scid;
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
