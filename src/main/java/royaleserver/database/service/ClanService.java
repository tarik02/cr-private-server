package royaleserver.database.service;

import royaleserver.database.entity.ClanEntity;
import royaleserver.database.entity.ClanType;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Set;

public class ClanService {
	private final EntityManager entityManager;

	public ClanService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ClanEntity add(ClanEntity entity){
		entityManager.getTransaction().begin();
		ClanEntity fromDB = entityManager.merge(entity);
		entityManager.getTransaction().commit();
		return fromDB;
	}

	public void update(ClanEntity entity){
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
	}

	public ClanEntity searchById(long id) {
		return (ClanEntity)entityManager.createNamedQuery("Clan.searchById").setParameter("id", id).getSingleResult();
	}

	public List<ClanEntity> searchByName(String name) {
		return entityManager.createNamedQuery("Clan.searchByName", ClanEntity.class).setParameter("name", name).getResultList();
	}

	public List<ClanEntity> search(String name, int minMembers, int maxMembers, int minTrophies, boolean onlyJoinable) {
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
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

		return entityManager.createQuery(query).getResultList();
	}
}
