package net.ocnl.rollout.geode;
import net.ocnl.rollout.IRolloutUser;

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
