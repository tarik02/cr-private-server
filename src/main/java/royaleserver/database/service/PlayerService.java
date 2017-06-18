package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.database.util.Transaction;
import royaleserver.logic.Arena;
import royaleserver.logic.ExpLevel;
import royaleserver.utils.StringUtils;

import java.util.Random;

public class PlayerService extends Service {
	private final Random random = new Random(System.currentTimeMillis());

	public PlayerService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public PlayerEntity create() {
		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setPassToken(StringUtils.randomString(32, 64));
		playerEntity.setTrophies(6400);
		playerEntity.setLogicArena(Arena.by("Arena_L9"));
		playerEntity.setLogicLastExpLevel(ExpLevel.by(13));
		playerEntity.setLogicExpLevel(ExpLevel.by(13));
		playerEntity.setGold(100000);
		playerEntity.setGems(100000);
		playerEntity.setRandomSeed(random.nextLong());
		playerEntity.setRareChance(random.nextFloat());
		playerEntity.setEpicChance(random.nextFloat());
		playerEntity.setLegendaryChance(random.nextFloat());
		playerEntity.setCurrentDeckSlot(0); // default
		return add(playerEntity);
	}

	public PlayerEntity add(PlayerEntity entity){
		try (Session session = session(); Transaction transaction = transaction(session)) {
			PlayerEntity fromDB = (PlayerEntity)session.merge(entity);
			transaction.commit();
			return fromDB;
		}
	}

	public PlayerEntity get(long id){
		try (Session session = session()) {
			return session.find(PlayerEntity.class, id);
		}
	}

	public void update(PlayerEntity entity){
		try (Session session = session(); Transaction transaction = transaction(session)) {
			session.update(entity);
			transaction.commit();
		}
	}

	public void clear() {
		try (Session session = session(); Transaction transaction = transaction(session)) {
			session.createNamedQuery(".clear").executeUpdate();
			transaction.commit();
		}
	}
}
