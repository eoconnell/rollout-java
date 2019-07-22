package net.ocnl.rollout;

import net.ocnl.rollout.SimpleRolloutTest.User;
import net.ocnl.rollout.storage.MapStorage;

public class SimpleRolloutTest extends RolloutContractTest<User> {

	public class User implements IRolloutUser {
		private long id;

		public User(long id) {
			this.id = id;
		}

		@Override
		public long getId() {
			return id;
		}
	}

	@Override
	public Rollout<User> createNewRolloutWithNoActiveFeatures() {
		return new Rollout<User>(new MapStorage());
	}

	@Override
	public User createNewRolloutUser(long id) {
		return new User(id);
	}

}
