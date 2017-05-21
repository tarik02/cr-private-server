package royaleserver.csv;

import royaleserver.utils.CSVConverter;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private Column[] cachedColumns;
	private Row[] cachedRows;
	private Value[][] cachedValues;

	private String[] columns;
	private String[] types;
	private String[][] table;

	public Table(String csv) {
		parse(csv);
	}

	public Table(byte[] buffer) {
		String csv = CSVConverter.decodeCSV(buffer);
		if (csv == null) {
			parse(new String(buffer));
		} else {
			parse(csv);
		}
	}

	public Column getColumn(int index) {
		if (cachedColumns[index] != null) {
			return cachedColumns[index];
		}

		return cachedColumns[index] = new Column(this, index);
	}

	public Column getColumn(String name) {
		for (int i = 0; i < columns.length; ++i) {
			if (columns[i].equals(name)) {
				return getColumn(i);
			}
		}

		return null;
	}

	public String getColumnName(int index) {
		return columns[index];
	}

	public String getColumnType(int index) {
		return types[index];
	}

	public Row getRow(int index) {
		if (cachedRows[index] != null) {
			return cachedRows[index];
		}

		return cachedRows[index] = new Row(this, index);
	}

	public Value getValue(int column, int row) {
		if (cachedValues[row][column] != null) {
			return cachedValues[row][column];
		}

		return cachedValues[row][column] = new Value(this, column, row);
	}

	public String getValueValue(int column, int row) {
		return table[row][column];
	}

	public Column[] getColumns() {
		for (int i = 0; i < columns.length; ++i) {
			getColumn(i);
		}

		return cachedColumns;
	}

	public Row[] getRows() {
		for (int i = 0; i < table.length; ++i) {
			getRow(i);
		}

		return cachedRows;
	}

	protected void parse(String csv) {
		String[] rows = csv.split(System.getProperty("line.separator"));
		columns = rows[0].split(",");
		for (int i = 0; i < columns.length; ++i) {
			columns[i] = removeQuotes(columns[i]);
		}

		types = rows[1].split(",");
		for (int i = 0; i < types.length; ++i) {
			types[i] = removeQuotes(types[i]);
		}

		List<String[]> resultRows = new ArrayList<>();
		String[] tableRow = null;
		for (int i = 2; i < rows.length; ++i) {
			String[] row = rows[i].split(",");

			if (row[0].trim().length() != 0) {
				if (tableRow != null) {
					resultRows.add(tableRow);
				}
				tableRow = new String[columns.length];
			}

			for (int j = 0; j < columns.length; ++j) {
				String type = types[j];
				String value = row[j];
				String unquotedValue = value;

				switch (type.toLowerCase()) {
				case "string":
				case "boolean":
					unquotedValue = removeQuotes(value);
					break;
				default:
					break;
				}

				if (tableRow[j] == null) {
					tableRow[j] = unquotedValue;
				} else if (unquotedValue.length() != 0) {
					tableRow[j] += '\n' + unquotedValue;
				}
			}
		}

		if (tableRow != null) {
			resultRows.add(tableRow);
		}
		table = resultRows.toArray(new String[0][]);

		cachedColumns = new Column[columns.length];
		cachedRows = new Row[table.length];
		cachedValues = new Value[table.length][];
		for (int i = 0; i < table.length; ++i) {
			cachedValues[i] = new Value[columns.length];
		}
	}

	protected static String removeQuotes(String input) {
		if (input.length() >= 2 && input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"') {
			return input.substring(1, input.length() - 1);
		}

		return input;
	}
}
