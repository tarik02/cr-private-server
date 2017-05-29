package royaleserver.database.entity;

import royaleserver.logic.ClanBadge;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clans")
@NamedQueries({
		@NamedQuery(name = "Clan.searchById", query = "SELECT clan FROM ClanEntity clan WHERE clan.id = :id"),
		@NamedQuery(name = "Clan.searchByName", query = "SELECT clan FROM ClanEntity clan WHERE LOWER(clan.name) LIKE LOWER(:name)")
})
public class ClanEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private String name = "";

	@Column(nullable = false, columnDefinition = "TEXT")
	private String description = "";

	@ManyToOne(optional = false)
	@JoinColumn(name = "badge_id")
	private ClanBadgeEntity badge;

	@Column(nullable = false)
	private int score = 0;

	@Column(nullable = false)
	private int donationsPerWeek = 0;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private ClanType type = ClanType.OPEN;

	@Column(nullable = false)
	private int requiredTrophies = 0;

	// TODO: Location

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "clan")
	private Set<PlayerEntity> members = new HashSet<>();


	public long getId() {
		return id;
	}

	public ClanEntity setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public ClanEntity setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public ClanEntity setDescription(String description) {
		this.description = description;
		return this;
	}

	public ClanBadgeEntity getBadge() {
		return badge;
	}

	public ClanEntity setBadge(ClanBadgeEntity badge) {
		this.badge = badge;
		return this;
	}

	public int getScore() {
		return score;
	}

	public ClanEntity setScore(int score) {
		this.score = score;
		return this;
	}

	public int getDonationsPerWeek() {
		return donationsPerWeek;
	}

	public ClanEntity setDonationsPerWeek(int donationsPerWeek) {
		this.donationsPerWeek = donationsPerWeek;
		return this;
	}

	public ClanType getType() {
		return type;
	}

	public ClanEntity setType(ClanType type) {
		this.type = type;
		return this;
	}

	public int getRequiredTrophies() {
		return requiredTrophies;
	}

	public ClanEntity setRequiredTrophies(int requiredTrophies) {
		this.requiredTrophies = requiredTrophies;
		return this;
	}

	public Set<PlayerEntity> getMembers() {
		return members;
	}

	public ClanEntity setMembers(Set<PlayerEntity> players) {
		this.members = players;
		return this;
	}


	public ClanBadge getLogicBadge() {
		return ClanBadge.byDB(badge.getId());
	}

	public ClanEntity setLogicBadge(ClanBadge badge) {
		return setBadge(new ClanBadgeEntity().setId(badge.getDbId()));
	}
}
