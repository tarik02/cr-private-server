package royaleserver.database.data;

import royaleserver.database.fields.Field;
import royaleserver.database.fields.Key;

public class PlayerData {
	public Key<Integer> id = new Key<>("id");
	public Field<Integer> gold = new Field<>("gold");
	public Field<Integer> gems = new Field<>("gems");

}

