package royaleserver.csv;

public class Value {
	private Table table;
	private int column, row;

	public Value(Table table, int column, int row) {
		this.table = table;
		this.column = column;
		this.row = row;
	}

	public int getColumnId() {
		return column;
	}

	public int getRowId() {
		return row;
	}

	public Column getColumn() {
		return table.getColumn(column);
	}

	public Row getRow() {
		return table.getRow(row);
	}

	public String getType() {
		return table.getColumnType(column);
	}

	public String getValue() {
		return table.getValueValue(column, row);
	}

	public String[] getValues() {
		return getValue().split("\n");
	}


	public boolean empty() {
		return getValue().trim().length() == 0;
	}

	public String asString() {
		return asString(false);
	}

	public String asString(boolean nullIfEmpty) {
		return (nullIfEmpty && empty()) ? null : getValue();
	}

	public String asString(String def) {
		return empty() ? def : asString();
	}

	public boolean asBoolean() {
		return getValue().equalsIgnoreCase("TRUE");
	}

	public boolean asBoolean(boolean def) {
		return empty() ? def : asBoolean();
	}

	public int asInt() {
		return Integer.valueOf(getValue());
	}

	public int asInt(int def) {
		return empty() ? def : asInt();
	}

	public Integer asIntNullable() {
		return empty() ? null : Integer.valueOf(getValue());
	}

	public float asFloat() {
		return Float.valueOf(getValue());
	}

	public float asFloat(float def) {
		return empty() ? def : asFloat();
	}

	public Float asFloatNullable() {
		return empty() ? null : Float.valueOf(getValue());
	}


	public String[] asStringArray() {
		return getValues();
	}

	public boolean[] asBooleanArray() {
		String[] values = getValues();
		boolean[] results = new boolean[values.length];

		for (int i = 0; i < values.length; ++i) {
			results[i] = values[i].equalsIgnoreCase("TRUE");
		}

		return results;
	}

	public int[] asIntArray() {
		String[] values = getValues();
		int[] results = new int[values.length];

		for (int i = 0; i < values.length; ++i) {
			results[i] = Integer.valueOf(values[i]);
		}

		return results;
	}

	public float[] asFloatArray() {
		String[] values = getValues();
		float[] results = new float[values.length];

		for (int i = 0; i < values.length; ++i) {
			results[i] = Float.valueOf(values[i]);
		}

		return results;
	}
}
