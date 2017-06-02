package royaleserver.logic;

import royaleserver.database.entity.LogicEntity;
import royaleserver.database.service.LogicService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class DBLogic extends NamedLogic {
	protected long dbId;

	public long getDbId() {
		return dbId;
	}

	protected static <
			Logic extends DBLogic,
			Entity extends LogicEntity,
			Service extends LogicService<Entity>
			> void init(List<Logic> values, Service service) {
		final Map<String, LogicEntity> entities = new HashMap<>();
		final HashMap<String, Long> entitiesToAdd = new HashMap<>();
		for (Entity entity : service.all()) {
			entities.put(entity.getName(), entity);
		}

		for (Logic logic : values) {
			LogicEntity entity = entities.getOrDefault(logic.getName(), null);
			if (entity == null) {
				entitiesToAdd.put(logic.getName(), 0L);
			} else {
				logic.dbId = entity.getId();
			}
		}

		if (entitiesToAdd.size() > 0) {
			service.store(entitiesToAdd);
			for (Map.Entry<String, Long> entry : entitiesToAdd.entrySet()) {
				for (Logic logic : values) {
					if (logic.name.equals(entry.getKey())) {
						logic.dbId = entry.getValue();
						break;
					}
				}
			}
		}
	}
}
