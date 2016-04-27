package com.evanoconnell.rollout.geode;
import com.evanoconnell.rollout.IRolloutUser;

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
