package royaleserver.csv;

public class Row {
	private Table table;
	private int index;

	public Row(Table table, int index) {
		this.table = table;
		this.index = index;
	}

	public Table getTable() {
		return table;
	}

	public int getIndex() {
		return index;
	}

	public Value getValue(int row) {
		return table.getValue(row, index);
	}

	public Value getValue(Column column) {
		if (table != column.getTable()) {
			return null;
		}

		return table.getValue(column.getIndex(), index);
	}
}
