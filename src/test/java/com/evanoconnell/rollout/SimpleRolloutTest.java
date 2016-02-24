package com.evanoconnell.rollout;

import com.evanoconnell.rollout.internal.MapStorage;

public class SimpleRolloutTest extends RolloutContractTest {

	@Override
	public Rollout createNewRolloutWithNoActiveFeatures() {
		return new Rollout(new MapStorage());
	}

}
