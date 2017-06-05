package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.PlayerEntity;
import royaleserver.database.util.Transaction;
import royaleserver.logic.Arena;
import royaleserver.logic.ExpLevel;
import royaleserver.utils.StringUtils;

import java.util.Date;
import java.util.Random;

public class PlayerService extends Service {
	private final Random random = new Random(System.currentTimeMillis());

	public PlayerService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public PlayerEntity create() {
		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setRegisteredDate(new Date());
		playerEntity.setLastOnlineStatusUpdate(new Date());
		playerEntity.setPassToken(StringUtils.randomString(32, 64));
		playerEntity.setTrophies(3800);
		playerEntity.setLogicArena(Arena.by("Arena_T"));
		playerEntity.setLogicLastExpLevel(ExpLevel.by(13));
		playerEntity.setLogicExpLevel(ExpLevel.by(13));
		playerEntity.setGold(10000);
		playerEntity.setGems(100000);
		playerEntity.setRandomSeed(random.nextLong());
		playerEntity.setRareChance(random.nextFloat());
		playerEntity.setEpicChance(random.nextFloat());
		playerEntity.setLegendaryChance(random.nextFloat());
		return add(playerEntity);
	}

	public PlayerEntity add(PlayerEntity entity){
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			PlayerEntity fromDB = (PlayerEntity)session.merge(entity);
			transaction.commit();
			return fromDB;
		}
	}

	public void delete(PlayerEntity entity){
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			session.remove(entity);
			transaction.commit();
		}
	}

	public PlayerEntity get(long id){
		try (Session session = getSession()) {
			return session.find(PlayerEntity.class, id);
		}
	}

	public void update(PlayerEntity entity){
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			session.merge(entity);
			transaction.commit();
		}
	}

	public void clear() {
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			session.createNamedQuery(".clear").executeUpdate();
			transaction.commit();
		}
	}
}
