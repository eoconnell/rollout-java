package com.evanoconnell.rollout;

public interface IRolloutStorage {

	void set(Object key, Object value);

	Object get(Object key);

}
