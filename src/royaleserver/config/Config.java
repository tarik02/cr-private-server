package royaleserver.config;

import java.util.HashMap;
import java.util.Map;

public class Config {
	public Map<String, String> paths = new HashMap<>();
	public Database database = new Database();

	public Config() {
		paths.put("assets", "./assets");
	}
}
