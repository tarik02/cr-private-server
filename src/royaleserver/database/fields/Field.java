package royaleserver.database.fields;

public class Field<T> {
	private final String fname;
	private T fvalue;

	private boolean fmodified;

	public Field(final String name) {
		this(name, null, false);
	}

	public Field(final String name, final T value) {
		this(name, value, true);
	}

	public Field(final String name, final T value, final boolean modified) {
		fname = name;
		fvalue = value;
		fmodified = modified;
	}

	public String name() {
		return fname;
	}

	public T value() {
		return fvalue;
	}

	public Field value(T value) {
		return this.value(value, true);
	}

	public Field value(T value, boolean modified) {
		fvalue = value;
		fmodified = modified;
		return this;
	}

	public boolean modified() {
		return fmodified;
	}

	public Field modified(boolean modified) {
		fmodified = modified;
		return this;
	}
}
