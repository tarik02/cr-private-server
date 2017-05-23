package royaleserver.csv;

public class Column {
	private Table table;
	private int index;

	public Column(Table table, int index) {
		this.table = table;
		this.index = index;
	}

	public Table getTable() {
		return table;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return table.getColumnName(index);
	}

	public String getType() {
		return table.getColumnType(index);
	}

	public Value getValue(int row) {
		return table.getValue(index, row);
	}

	public Value getValue(Row row) {
		if (table != row.getTable()) {
			return null;
		}

		return table.getValue(index, row.getIndex());
	}
}
