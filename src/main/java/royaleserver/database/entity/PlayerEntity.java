package royaleserver.database.entity;

import royaleserver.database.util.Identifiable;
import royaleserver.logic.Arena;
import royaleserver.logic.ClanRole;
import royaleserver.logic.ExpLevel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "players")
public class PlayerEntity implements Identifiable<Long>, Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 32, nullable = true)
	private String name;

	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date registeredDate = new Date();

	@Column(nullable = false)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastOnlineStatusUpdate = new Date();

	@Column(nullable = false)
	private boolean online = false;

	@Column(nullable = false)
	private int gold;

	@Column(nullable = false)
	private int gems;

	@Column(length = 64, nullable = false)
	private String passToken; // Account security, like password

	@Column(nullable = false)
	@OneToMany(mappedBy = "player")
	private Set<PlayerCardEntity> cards = new HashSet<>();

	@Column(nullable = false)
	private int trophies = 0;

	@JoinColumn(name = "arena_id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ArenaEntity arena;


	@JoinColumn(name = "last_exp_level_id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ExpLevelEntity lastExpLevel;
	
	@JoinColumn(name = "exp_level_id")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private ExpLevelEntity expLevel;

	@Column(name = "exp_level_experience", nullable = false)
	private int expLevelExperience = 0;

	@Column(nullable = false)
	@OneToMany(mappedBy = "player")
	private Set<HomeChestEntity> homeChests = new HashSet<>();

	@JoinColumn(name = "clan_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private ClanEntity clan = null;

	@JoinColumn(name = "clan_role_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private ClanRoleEntity clanRole = null;

	private long randomSeed;

	private float rareChance = 0;
	private float epicChance = 0;
	private float legendaryChance = 0;

	private int currentDeckSlot = 0;

	@OneToMany(mappedBy = "player")
	private Set<PlayerDeckCardEntity> decksCards;


	public PlayerEntity() {
		this.registeredDate = new Date();
		this.lastOnlineStatusUpdate = new Date();
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PlayerEntity setName(String name) {
		this.name = name;
		return this;
	}

	public Date getRegisteredDate() {
		return registeredDate;
	}

	public Date getLastOnlineStatusUpdate() {
		return lastOnlineStatusUpdate;
	}

	public PlayerEntity setLastOnlineStatusUpdate(Date lastOnlineStatusUpdate) {
		this.lastOnlineStatusUpdate = lastOnlineStatusUpdate;
		return this;
	}

	public boolean isOnline() {
		return online && getLastOnlineStatusUpdate().after(new Date(System.currentTimeMillis() - 5 * 60 * 1000));
	}

	public PlayerEntity setOnline(boolean online) {
		this.online = online;
		return setLastOnlineStatusUpdate(new Date());
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

	public int getTrophies() {
		return trophies;
	}

	public PlayerEntity setTrophies(int trophies) {
		this.trophies = trophies;
		return this;
	}

	public ArenaEntity getArena() {
		return arena;
	}

	public PlayerEntity setArena(ArenaEntity arena) {
		this.arena = arena;
		return this;
	}

	public Arena getLogicArena() {
		return Arena.byDB(arena.getId());
	}

	public PlayerEntity setLogicArena(Arena arena) {
		return setArena(arena.getDbEntity());
	}

	public ExpLevelEntity getLastExpLevel() {
		return lastExpLevel;
	}

	public PlayerEntity setLastExpLevel(ExpLevelEntity lastExpLevel) {
		this.lastExpLevel = lastExpLevel;
		return this;
	}

	public ExpLevel getLogicLastExpLevel() {
		return ExpLevel.byDB(lastExpLevel.getId());
	}

	public PlayerEntity setLogicLastExpLevel(ExpLevel level) {
		return setLastExpLevel(level.getDbEntity());
	}
	
	public ExpLevelEntity getExpLevel() {
		return expLevel;
	}

	public PlayerEntity setExpLevel(ExpLevelEntity expLevel) {
		this.expLevel = expLevel;
		return this;
	}
	
	public ExpLevel getLogicExpLevel() {
		return ExpLevel.byDB(expLevel.getId());
	}

	public PlayerEntity setLogicExpLevel(ExpLevel level) {
		return setExpLevel(level.getDbEntity());
	}

	public int getExpLevelExperience() {
		return expLevelExperience;
	}

	public PlayerEntity setExpLevelExperience(int expLevelExperience) {
		this.expLevelExperience = expLevelExperience;
		return this;
	}

	public Set<HomeChestEntity> getHomeChests() {
		return homeChests;
	}

	public PlayerEntity setHomeChests(Set<HomeChestEntity> homeChests) {
		this.homeChests = homeChests;
		return this;
	}

	public ClanEntity getClan() {
		return clan;
	}

	public PlayerEntity setClan(ClanEntity clan) {
		this.clan = clan;
		return this;
	}

	public ClanRoleEntity getClanRole() {
		return clanRole;
	}

	public PlayerEntity setClanRole(ClanRoleEntity clanRole) {
		this.clanRole = clanRole;
		return this;
	}

	public ClanRole getLogicClanRole() {
		return ClanRole.byDB(clanRole.getId());
	}

	public PlayerEntity setLogicClanRole(ClanRole role) {
		return setClanRole(role.getDbEntity());
	}

	public float getRareChance() {
		return rareChance;
	}

	public PlayerEntity setRareChance(float rareChance) {
		this.rareChance = rareChance;
		return this;
	}

	public long getRandomSeed() {
		return randomSeed;
	}

	public PlayerEntity setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
		return this;
	}

	public float getEpicChance() {
		return epicChance;
	}

	public PlayerEntity setEpicChance(float epicChance) {
		this.epicChance = epicChance;
		return this;
	}

	public float getLegendaryChance() {
		return legendaryChance;
	}

	public PlayerEntity setLegendaryChance(float legendaryChance) {
		this.legendaryChance = legendaryChance;
		return this;
	}

	public int getCurrentDeckSlot() {
		return currentDeckSlot;
	}

	public void setCurrentDeckSlot(int currentDeckSlot) {
		this.currentDeckSlot = currentDeckSlot;
	}

	public Set<PlayerDeckCardEntity> getDecksCards() {
		return decksCards;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof PlayerEntity)) {
			return false;
		}

		PlayerEntity that = (PlayerEntity)o;

		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
