package royaleserver.logic;

import royaleserver.database.entity.LogicEntity;
import royaleserver.database.service.LogicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DBLogic<Entity extends LogicEntity> extends NamedLogic {
	protected long dbId;
	protected Entity dbEntity;

	public final long getDbId() {
		return dbId;
	}

	public final Entity getDbEntity() {
		return dbEntity;
	}

	protected static <
			Logic extends DBLogic,
			Entity extends LogicEntity,
			Service extends LogicService<Entity>
			> void init(List<Logic> values, Service service) {
		final Map<String, LogicEntity> entities = new HashMap<>();
		final HashMap<String, Entity> entitiesToAdd = new HashMap<>();
		for (Entity entity : service.all()) {
			entities.put(entity.getName().toLowerCase(), entity);
		}

		for (Logic logic : values) {
			LogicEntity entity = entities.getOrDefault(logic.getName().toLowerCase(), null);
			if (entity == null) {
				entitiesToAdd.put(logic.getName().toLowerCase(), null);
			} else {
				logic.dbId = entity.getId();
				logic.dbEntity = entity;
			}
		}

		if (entitiesToAdd.size() > 0) {
			service.store(entitiesToAdd);
			for (Map.Entry<String, Entity> entry : entitiesToAdd.entrySet()) {
				for (Logic logic : values) {
					if (logic.name.equals(entry.getKey())) {
						logic.dbEntity = entry.getValue();
						logic.dbId = logic.dbEntity.getId();
						break;
					}
				}
			}
		}
	}
}
