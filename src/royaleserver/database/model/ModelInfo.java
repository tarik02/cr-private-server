package royaleserver.database.model;

public class ModelInfo<T extends Model> {
	public final String table;
	public final Class<T> clazz;
	public final String[] keys;

	public ModelInfo(String table, Class<T> clazz, String[] keys) {
		this.table = table;
		this.clazz = clazz;
		this.keys = keys;
	}
}
