package com.evanoconnell.rollout.storage;

public interface IRolloutStorage {

	void write(String key, String value);

	String read(String key);

}
