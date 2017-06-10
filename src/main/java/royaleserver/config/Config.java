package royaleserver.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public final class Config {
	public static final int CONFIG_VERSION = 4;
	public static final String CONTENT_HASH = "e99dcd87d0f3ba976efd982ef51a7f393463aeca";

	private Map<String, JsonElement> map = new HashMap<>();

	public Config(JsonObject json) {
		fill("", json);
	}

	public JsonElement get(String key) {
		return get(key, null);
	}

	public JsonElement get(String key, JsonElement def) {
		return map.getOrDefault(key, def);
	}

	private void fill(String prefix, JsonObject json) {
		for (Map.Entry<String, JsonElement> child : json.entrySet()) {
			String key = child.getKey();
			JsonElement element = child.getValue();

			if (element instanceof JsonObject) {
				fill(prefix + key + ".", (JsonObject)element);
			} else {
				map.put(prefix + key, element);
			}
		}
	}
}
