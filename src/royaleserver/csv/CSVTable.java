package royaleserver.csv;

import royaleserver.utils.CSVConverter;

import java.io.StringReader;

public class CSVTable {
	private String[] columns;
	private String[] types;
	private String[][] table;

	public CSVTable(String csv) {
		parse(csv);
	}

	public CSVTable(byte[] buffer) {
		String csv = CSVConverter.decodeCSV(buffer);
		if (csv == null) {
			parse(new String(buffer));
		} else {
			parse(csv);
		}
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

		for (int i = 2; i < rows.length; ++i) {
			String[] row = rows[i].split(",");

		}
	}

	protected static String removeQuotes(String input) {
		if (input.length() >= 2 && input.charAt(0) == '"' && input.charAt(input.length() - 1) == '"') {
			return input.substring(1, input.length() - 2);
		}

		return input;
	}
}
