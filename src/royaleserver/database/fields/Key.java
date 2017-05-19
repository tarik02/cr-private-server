package royaleserver.database.fields;

public class Key<T> extends Field<T> {
	public Key(final String name) {
		super(name);
	}

	public Key(final String name, final T value) {
		super(name, value);
	}

	public Key(final String name, final T value, final boolean modified) {
		super(name, value, modified);
	}

	@Override
	public Field value(T value) {
		throw new RuntimeException("Key field cannot be modified.");
	}
}
