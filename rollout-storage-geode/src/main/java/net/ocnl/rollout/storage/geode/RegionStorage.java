package net.ocnl.rollout.storage.geode;

import net.ocnl.rollout.storage.IRolloutStorage;
import com.gemstone.gemfire.cache.Region;

public class RegionStorage implements IRolloutStorage {

	private Region<Object, Object> region;

	public RegionStorage(Region<Object, Object> region) {
		this.region = region;
	}

	@Override
	public void write(String key, String value) {
		region.put(key, value);
	}

	@Override
	public String read(String key) {
		return (String) region.get(key);
	}
}
