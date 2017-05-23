package royaleserver.protocol.messages;

import royaleserver.protocol.Session;
import royaleserver.utils.DataStream;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandFactory {
	private CommandFactory() {}

	private static Map<Integer, Class<? extends Command>> registeredCommands = new HashMap<>();

	public static void register(Class<? extends Command> clazz) {
		try {
			int id = clazz.getField("ID").getShort(null);
			registeredCommands.put(id, clazz);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public static Command create(int id) {
		return create(id, null);
	}

	public static Command create(int id, DataStream stream) {
		Class<?> clazz = null;
		if (registeredCommands.containsKey(id)) {
			clazz = registeredCommands.get(id);
		}

		if (clazz == null) {
			return null;
		}

		Command command = null;
		try {
			command = (Command)clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}

		if (command != null && stream != null) {
			command.decode(stream);
		}

		return command;
	}

	static {
		Reflections reflections = new Reflections("royaleserver.protocol.messages");

		Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);

		for (Class<? extends Command> clazz : allClasses) {
			register(clazz);
		}
	}
}
