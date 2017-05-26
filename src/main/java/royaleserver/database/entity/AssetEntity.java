package royaleserver.database.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "assets")
@NamedQuery(name = "getAssetByName", query = "SELECT c FROM AssetEntity c WHERE c.name = :name")
public class AssetEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 128, unique = true, nullable = false)
	private String name;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Timestamp lastUpdated;

	public long getId() {
		return id;
	}

	public AssetEntity setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public AssetEntity setName(String name) {
		this.name = name;
		return this;
	}

	public Timestamp getLastUpdated() {
		return lastUpdated;
	}

	public AssetEntity setLastUpdated(Timestamp lastUpdated) {
		this.lastUpdated = lastUpdated;
		return this;
	}
}
