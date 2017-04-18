package com.tarik02.clashroyale.server.utils;

public class ChildLogger extends Logger {
	private final Logger parent;

	public ChildLogger(Logger parent, String name) {
		super(name);
		this.parent = parent;
	}

	@Override
	protected void send(LogLevel level, String name, String message, Object[] format) {
		parent.send(level, (parent.name().isEmpty() ? "" : (parent.name() + ".")) + name, message, format);
	}
}
