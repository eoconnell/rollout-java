package com.evanoconnell.rollout.storage;

import com.evanoconnell.rollout.storage.IRolloutStorage;
import com.evanoconnell.rollout.storage.MapStorage;
import com.evanoconnell.rollout.storage.StorageContractTest;

public class MapStorageTest extends StorageContractTest {

	@Override
	public IRolloutStorage createNewStorage() {
		return new MapStorage();
	}

}
