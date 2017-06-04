package royaleserver.database.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import royaleserver.database.entity.ClanEntity;
import royaleserver.database.entity.ClanType;
import royaleserver.database.util.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class ClanService extends Service {
	public ClanService(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public ClanEntity add(ClanEntity entity){
		try (Session session = getSession(); Transaction transaction = transaction(session)) {
			ClanEntity fromDB = (ClanEntity)session.merge(entity);
			transaction.commit();
			return fromDB;
		}
	}

	public ClanEntity searchById(long id) {
		try (Session session = getSession()) {
			return session.createNamedQuery("ClanEntity.byId", ClanEntity.class).setParameter("id", id).getSingleResult();
		}
	}

	public List<ClanEntity> searchByName(String name) {
		try (Session session = getSession()) {
			return session.createNamedQuery("ClanEntity.byName", ClanEntity.class).setParameter("name", name).getResultList();
		}
	}

	public List<ClanEntity> search(String name, int minMembers, int maxMembers, int minTrophies, boolean onlyJoinable) {
		try (Session session = getSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<ClanEntity> query = builder.createQuery(ClanEntity.class);
			Root<ClanEntity> root = query.from(ClanEntity.class);
			query.select(root);

			if (name != null && !name.isEmpty()) {
				query.where(builder.like(root.get("name"), "%" + name + "%"));
			}

			// TODO: Check min and max members

			if (minTrophies != 0) {
				query.where(builder.greaterThan(root.get("score"), minTrophies));
			}

			if (onlyJoinable) {
				query.where(builder.equal(root.get("type"), builder.literal(ClanType.OPEN)));
				// TODO: Check members
			}

			return session.createQuery(query).getResultList();
		}
	}
}
