package net.ocnl.rollout.geode;


import net.ocnl.rollout.Rollout;
import net.ocnl.rollout.storage.geode.RegionStorage;
import com.gemstone.gemfire.cache.Region;
import com.gemstone.gemfire.cache.client.ClientCache;
import com.gemstone.gemfire.cache.client.ClientCacheFactory;

public class ClientDriver {

	public static void main(String[] args) {

		ClientCache cache = new ClientCacheFactory()
				.set("cache-xml-file", "src/test/resources/client-cache.xml")
				.create();

		Region<Object,Object> region = cache.getRegion("rollout");

		Rollout<User> rollout = new Rollout<>(new RegionStorage(region));

		User user = new User(1);

		boolean r1 = rollout.isActive("chat", user); // => false
		System.out.println(r1);

		rollout.activateGroup("chat", "all");

		boolean r2 = rollout.isActive("chat", user); // => true
		System.out.println(r2);

		Object x = region.get("feature:chat");
		System.out.println(x.getClass());
		System.out.println(x);

		cache.close();
	}
}
