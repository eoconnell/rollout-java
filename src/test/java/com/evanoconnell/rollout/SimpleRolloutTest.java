package com.evanoconnell.rollout;

import com.evanoconnell.rollout.internal.MapStorage;
import com.evanoconnell.rollout.internal.User;

public class SimpleRolloutTest extends RolloutContractTest<User> {

	@Override
	public Rollout<User> createNewRolloutWithNoActiveFeatures() {
		return new Rollout<User>(new MapStorage());
	}

	@Override
	public User createNewRolloutUser(long id) {
		return new User(id);
	}

}
