package com.evanoconnell.rollout.storage;

public interface IRolloutStorage {

	void set(Object key, Object value);

	Object get(Object key);

}
