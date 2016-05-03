package com.evanoconnell.rollout.storage.geode;

import com.evanoconnell.rollout.storage.IRolloutStorage;
import com.gemstone.gemfire.cache.Region;

public class RegionStorage implements IRolloutStorage {

	private Region<Object, Object> region;

	public RegionStorage(Region<Object, Object> region) {
		this.region = region;
	}

	@Override
	public void set(Object key, Object value) {
		region.put(key, value);
	}

	@Override
	public Object get(Object key) {
		return region.get(key);
	}
}
