package com.evanoconnell.rollout.storage;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public abstract class StorageContractTest {

	public abstract IRolloutStorage createNewStorage();

	@Test
	public void it_can_get_stored_features_by_key() {
		IRolloutStorage storage = createNewStorage();

		storage.set("key", "value");

		assertThat(storage.get("key"), is((Object) "value"));
	}

	@Test
	public void the_value_of_an_unknown_key_is_null() {
		IRolloutStorage storage = createNewStorage();

		assertThat(storage.get("key"), is(nullValue()));
	}

}