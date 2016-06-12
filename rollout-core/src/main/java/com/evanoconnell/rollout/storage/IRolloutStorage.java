package com.evanoconnell.rollout.storage;

public interface IRolloutStorage {

	void set(String key, String value);

	String get(String key);

}
