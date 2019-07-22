package net.ocnl.rollout.storage;

import java.util.HashMap;
import java.util.Map;

public class MapStorage implements IRolloutStorage {

	private Map<String, String> map;

	public MapStorage() {
		this.map = new HashMap<String, String>();
	}

	public void write(String key, String value) {
		map.put(key, value);
	}

	public String read(String key) {
		return map.get(key);
	}
}
