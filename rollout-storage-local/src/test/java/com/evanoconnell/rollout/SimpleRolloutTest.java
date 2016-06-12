package com.evanoconnell.rollout;

import com.evanoconnell.rollout.SimpleRolloutTest.User;
import com.evanoconnell.rollout.storage.MapStorage;

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
