package royaleserver.database.service;

import royaleserver.database.entity.ClanEntity;

import javax.persistence.EntityManager;
import java.util.List;

public class ClanService {
	private final EntityManager entityManager;

	public ClanService(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public ClanEntity searchById(long id) {
		return (ClanEntity)entityManager.createNamedQuery("Clan.searchById").setParameter("id", id).getSingleResult();
	}

	public List<ClanEntity> searchByName(String name) {
		return (List<ClanEntity>)entityManager.createNamedQuery("Clan.searchByName").setParameter("name", name).getResultList();
	}
}
