package royaleserver.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import royaleserver.ServerException;
import royaleserver.utils.GsonUtils;
import royaleserver.utils.IO;
import royaleserver.utils.LogManager;
import royaleserver.utils.Logger;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class Config {
	private static final Logger logger = LogManager.getLogger(Config.class);

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

	public static Config load(int configVersion, File configFile, Function<Void, InputStream> defaultConfigFactory) throws ServerException {
		try {
			int version = 0;

			JsonObject configObject = null;
			if (configFile.exists()) {
				try {
					configObject = new JsonParser().parse(new InputStreamReader(new FileInputStream(configFile))).getAsJsonObject();
					JsonElement jversion = configObject.get("_version");
					if (jversion != null && !jversion.isJsonNull()) {
						version = jversion.getAsInt();
					}
				} catch (Exception ignored) {}
			}

			if (version == 0 || version < configVersion) {
				InputStream configInputStream = defaultConfigFactory.apply(null);
				if (configInputStream == null) {
					throw new ServerException("Failed to get default config resource.");
				}

				if (version == 0) {
					logger.warn("Resetting your config...");

					final byte[] bytes = IO.getByteArray(configInputStream, true);
					if (bytes == null) {
						throw new ServerException("Failed to get default config.");
					}

					configObject = new JsonParser().parse(new InputStreamReader(new ByteArrayInputStream(bytes))).getAsJsonObject();
					try (OutputStream os = new FileOutputStream(configFile)) {
						os.write(bytes);
					}
				} else if (version < configVersion) {
					JsonObject defaultConfig = new JsonParser().parse(new InputStreamReader(configInputStream)).getAsJsonObject();
					GsonUtils.extendJsonObject(defaultConfig, GsonUtils.ConflictStrategy.PREFER_FIRST_OBJ, configObject);
					configObject = defaultConfig;
				}

				configObject.addProperty("_version", configVersion);

				logger.warn("Saving config...");
				JsonWriter writer = new JsonWriter(new FileWriter(configFile));
				writer.setIndent("\t");
				new Gson().toJson(configObject, writer);
				writer.close();

				logger.warn("Check out your config and start the server.");
				return null;
			}

			return new Config(configObject);
		} catch (Throwable e) {
			logger.fatal("Cannot read config.", e);
			throw new ServerException("Cannot read config.");
		}
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
