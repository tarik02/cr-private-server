package royaleserver.database.model;

import royaleserver.database.DataManager;

public class Model {
	private Object[] properties;

	public Model() {
		properties = new Object[DataManager.getModelInfo(getClass()).keys.length];
	}

	public Model(Object[] properties) {
		if (properties.length != DataManager.getModelInfo(getClass()).keys.length) {
			throw new RuntimeException("Invalid properties.");
		}

		this.properties = properties;
	}

	public <T> T get(int key) {
		return (T)properties[key];
	}

	public <T> Model set(int key, T value) {
		properties[key] = value;
		return this;
	}
}
