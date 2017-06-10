package royaleserver.database.entity;

import royaleserver.logic.Chest;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "home_chest", indexes = {
		@Index(unique = true, columnList = "player_id,slot")
})
public class HomeChestEntity implements java.io.Serializable {
	@Id
	@ManyToOne
	@PrimaryKeyJoinColumn(name = "player_id", referencedColumnName = "id")
	private PlayerEntity player;

	@Id
	private int slot;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "chest_id", referencedColumnName = "id")
	private ChestEntity chest;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private HomeChestStatus status;

	@Column(nullable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date openStart = null;

	@Column(nullable = true)
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date openEnd = null;


	public PlayerEntity getPlayer() {
		return player;
	}

	public HomeChestEntity setPlayer(PlayerEntity player) {
		this.player = player;
		return this;
	}

	public int getSlot() {
		return slot;
	}

	public HomeChestEntity setSlot(int slot) {
		this.slot = slot;
		return this;
	}

	public ChestEntity getChest() {
		return chest;
	}

	public HomeChestEntity setChest(ChestEntity chest) {
		this.chest = chest;
		return this;
	}

	public HomeChestStatus getStatus() {
		return status;
	}

	public HomeChestEntity setStatus(HomeChestStatus status) {
		this.status = status;
		return this;
	}

	public Date getOpenStart() {
		return openStart;
	}

	public HomeChestEntity setOpenStart(Date openStart) {
		this.openStart = openStart;
		return this;
	}

	public Date getOpenEnd() {
		return openEnd;
	}

	public HomeChestEntity setOpenEnd(Date openEnd) {
		this.openEnd = openEnd;
		return this;
	}

	public Chest getLogicChest() {
		return Chest.byDB(chest.getId());
	}

	public HomeChestEntity setLogicChest(Chest chest) {
		return setChest(new ChestEntity().setId(chest.getDbId()));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof HomeChestEntity)) {
			return false;
		}

		HomeChestEntity that = (HomeChestEntity)o;

		return slot == that.slot && player.equals(that.player);
	}

	@Override
	public int hashCode() {
		int result = player.hashCode();
		result = 31 * result + slot;
		return result;
	}
}
