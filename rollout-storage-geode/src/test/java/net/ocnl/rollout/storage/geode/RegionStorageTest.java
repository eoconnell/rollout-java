package net.ocnl.rollout.storage.geode;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import net.ocnl.rollout.storage.IRolloutStorage;
import net.ocnl.rollout.storage.StorageContractTest;
import com.gemstone.gemfire.cache.Cache;
import com.gemstone.gemfire.cache.CacheFactory;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.RegionShortcut;

public class RegionStorageTest extends StorageContractTest {

	private static Cache cache;

	@BeforeClass
	public static void beforeAll() {
		cache = new CacheFactory().create();
	}

	@AfterClass
	public static void afterAll() {
		cache.close();
	}

	private Region<Object,Object> region;

	@Before
	public void beforeEach() {
		region = cache.createRegionFactory(RegionShortcut.LOCAL)
		              .create("testRegion");
	}

	@After
	public void afterEach() {
		region.close();
	}

	@Override
	public IRolloutStorage createNewStorage() {
		return new RegionStorage(region);
	}

}
