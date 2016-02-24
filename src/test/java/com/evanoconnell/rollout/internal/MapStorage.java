package com.evanoconnell.rollout.internal;

import java.util.HashMap;
import java.util.Map;

import com.evanoconnell.rollout.IRolloutStorage;

public class MapStorage implements IRolloutStorage {

	private Map<Object, Object> map;

	public MapStorage() {
		this.map = new HashMap<Object,Object>();
	}

	public void set(Object key, Object value) {
		map.put(key, value);
	}

	public Object get(Object key) {
		return map.get(key);
	}
}