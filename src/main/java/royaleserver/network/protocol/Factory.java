package royaleserver.network.protocol;

import org.reflections.Reflections;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public abstract class Factory<KeyType, TargetClass extends FactoryTarget> {
	private static final Logger logger = LogManager.getLogger(Factory.class);

	private final String packagePrefix;
	private final Class<TargetClass> targetClass;

	private final Map<KeyType, FactoryTarget<TargetClass>> creators = new HashMap<>();
	private boolean initialized = false;

	protected Factory(String packagePrefix, Class<TargetClass> targetClass) {
		this.packagePrefix = packagePrefix;
		this.targetClass = targetClass;
	}

	public final void init() {
		if (initialized) {
			return;
		}
		initialized = true;

		for (Class<? extends TargetClass> clazz : new Reflections(packagePrefix).getSubTypesOf(targetClass)) {
			int modifiers = clazz.getModifiers();

			if (Modifier.isAbstract(modifiers)) {
				continue;
			}

			try {
				KeyType key = (KeyType)clazz.getDeclaredField("ID").get(null);
				FactoryTarget<TargetClass> value = null;
				try {
					value = clazz.newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					logger.error("Cannot create object of %s.", e, clazz.getName());
					continue;
				}

				if (!clazz.equals(value.create().getClass())) {
					logger.fatal("Class %s has bad FactoryTarget.create.", clazz.getName());
				}

				creators.put(key, value);
			} catch (IllegalAccessException | NoSuchFieldException e) {
				logger.error("Class %s must have static field ID.", e, clazz.getName());
			}
		}
	}

	public final TargetClass create(KeyType key) {
		FactoryTarget<TargetClass> creator = creators.getOrDefault(key, null);
		return creator == null ? null : creator.create();
	}
}
