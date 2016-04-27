package com.evanoconnell.rollout;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.util.Set;

import org.junit.Test;

public class FeatureTest {

	@Test
	public void getGroups_returns_a_new_set() {
		Feature f = new Feature("chat");
		f.addGroup("all");

		Set<String> groups = f.getGroups();

		f.addGroup("admins");

		assertThat(groups, contains("all"));
	}
}
