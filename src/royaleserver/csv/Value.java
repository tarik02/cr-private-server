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

	public String asString() {
		return getValue();
	}

	public boolean asBool() {
		return getValue() == "TRUE";
	}

	public int asInt() {
		return Integer.valueOf(getValue());
	}

	public float asFloat() {
		return Float.valueOf(getValue());
	}
}
