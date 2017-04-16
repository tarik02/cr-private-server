package com.tarik02.clashroyale.server.protocol.messages;

import com.tarik02.clashroyale.server.utils.DataStream;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MessageFactory {
	private MessageFactory() {}

	private static Map<Short, Class<? extends Message>> registeredMessages = new HashMap<>();

	public static void register(Class<? extends Message> clazz) {
		try {
			short id = clazz.getField("ID").getShort(null);
			registeredMessages.put(id, clazz);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public static Message create(short id) {
		return create(id, null);
	}

	public static Message create(short id, DataStream stream) {
		Class<?> clazz = registeredMessages.getOrDefault(id, null);
		if (clazz == null) {
			return null;
		}

		Message message = null;
		try {
			message = (Message)clazz.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}

		if ((message != null) && (stream != null)) {
			message.decode(stream);
		}

		return message;
	}

	static {
		Reflections reflections = new Reflections("com.tarik02.clashroyale.server.protocol.messages");

		Set<Class<? extends Message>> allClasses = reflections.getSubTypesOf(Message.class);
		for (Class<? extends Message> clazz : allClasses) {
			register(clazz);
		}
	}
}
