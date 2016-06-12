package com.evanoconnell.rollout.storage.geode;

import com.evanoconnell.rollout.storage.IRolloutStorage;
import com.gemstone.gemfire.cache.Region;

public class RegionStorage implements IRolloutStorage {

	private Region<Object, Object> region;

	public RegionStorage(Region<Object, Object> region) {
		this.region = region;
	}

	@Override
	public void set(String key, String value) {
		region.put(key, value);
	}

	@Override
	public String get(String key) {
		return (String) region.get(key);
	}
}
