package com.evanoconnell.rollout;

import com.evanoconnell.rollout.internal.MapStorage;

public class MapStorageTest extends StorageContractTest {

	@Override
	public IRolloutStorage createNewStorage() {
		return new MapStorage();
	}

}
