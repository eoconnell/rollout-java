package net.ocnl.rollout.storage;

import net.ocnl.rollout.storage.IRolloutStorage;
import net.ocnl.rollout.storage.MapStorage;
import net.ocnl.rollout.storage.StorageContractTest;

public class MapStorageTest extends StorageContractTest {

	@Override
	public IRolloutStorage createNewStorage() {
		return new MapStorage();
	}

}
