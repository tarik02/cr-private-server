package royaleserver.network.protocol;

import org.reflections.Reflections;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.lang.reflect.Modifier;
import java.util.Map;

public abstract class Factory<KeyType, TargetClass> {
	private static final Logger logger = LogManager.getLogger(Factory.class);

	protected interface Creator<TargetClass> {
		TargetClass create();
	}

	private Map<KeyType, Creator<TargetClass>> creators;

	protected Factory(String packagePrefix, Class<TargetClass> targetClass) {
		for (Class<? extends TargetClass> clazz : new Reflections(packagePrefix).getSubTypesOf(targetClass)) {
			int modifiers = clazz.getModifiers();

			if (Modifier.isAbstract(modifiers)) {
				continue;
			}

			if (!Modifier.isFinal(modifiers)) {
				logger.warn("The class %s should be final.", clazz.getName());
			}

			try {
				KeyType key = (KeyType)clazz.getDeclaredField("ID").get(null);
				Creator<TargetClass> value = () -> {
					try {
						return clazz.newInstance();
					} catch (InstantiationException | IllegalAccessException e) {
						logger.error("Cannot create object of %s.", e, clazz.getName());
						return null;
					}
				};

				creators.put(key, value);
			} catch (IllegalAccessException | NoSuchFieldException e) {
				logger.error("Class %s must have static field ID.", e, clazz.getName());
			}
		}
	}

	public final TargetClass create(KeyType key) {
		Creator<TargetClass> creator = creators.getOrDefault(key, null);
		return creator == null ? null : creator.create();
	}
}
