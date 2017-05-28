package royaleserver.database.service;

import royaleserver.database.entity.PlayerEntity;
import royaleserver.logic.Arena;
import royaleserver.utils.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class PlayerService {
	private final EntityManager entityManager;

	public PlayerService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public PlayerEntity create() {
		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setPassToken(StringUtils.randomString(32, 64));
		playerEntity.setLogicArena(Arena.by("Arena1"));
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

	public List<PlayerEntity> getAll(){
		TypedQuery<PlayerEntity> namedQuery = entityManager.createNamedQuery(".getAll", PlayerEntity.class);
		return namedQuery.getResultList();
	}

	public void clear() {
		entityManager.getTransaction().begin();
		entityManager.createNamedQuery(".clear").executeUpdate();
		entityManager.getTransaction().commit();
	}
}
