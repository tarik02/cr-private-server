package royaleserver.database.service;

import royaleserver.database.entity.PlayerEntity;
import royaleserver.logic.Arena;
import royaleserver.logic.ExpLevel;
import royaleserver.utils.StringUtils;

import javax.persistence.EntityManager;
import java.util.Date;

public class PlayerService {
	private final EntityManager entityManager;

	public PlayerService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public PlayerEntity create() {
		return create(null, null);
	}

	public PlayerEntity create(Long id, String passToken) {
		PlayerEntity playerEntity = new PlayerEntity();
		if (id != null) {
			playerEntity.setId(id);
		}
		playerEntity.setRegisteredDate(new Date());
		playerEntity.setPassToken(passToken == null ? StringUtils.randomString(32, 64) : passToken);
		playerEntity.setTrophies(3800);
		playerEntity.setLogicArena(Arena.by("Arena_T"));
		playerEntity.setLogicLastExpLevel(ExpLevel.by(13));
		playerEntity.setLogicExpLevel(ExpLevel.by(13));
		playerEntity.setGold(10000);
		playerEntity.setGems(100000);
		return add(playerEntity);
	}

	public PlayerEntity add(PlayerEntity entity){
		entityManager.getTransaction().begin();
		PlayerEntity fromDB = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return fromDB;
	}

	public void delete(PlayerEntity entity){
		entityManager.getTransaction().begin();
		entityManager.remove(entity);
		entityManager.getTransaction().commit();
	}

	public PlayerEntity get(long id){
		return entityManager.find(PlayerEntity.class, id);
	}

	public void update(PlayerEntity entity){
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
	}

	public void clear() {
		entityManager.getTransaction().begin();
		entityManager.createNamedQuery(".clear").executeUpdate();
		entityManager.getTransaction().commit();
	}
}
