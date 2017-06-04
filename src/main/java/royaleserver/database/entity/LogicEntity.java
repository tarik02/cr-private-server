package royaleserver.database.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class LogicEntity<Self extends LogicEntity> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false, unique = true)
	private String name;

	public LogicEntity() {}

	public LogicEntity(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Self setId(long id) {
		this.id = id;
		return (Self)this;
	}

	public String getName() {
		return name;
	}

	public Self setName(String name) {
		this.name = name;
		return (Self)this;
	}
}
