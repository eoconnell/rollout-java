package com.evanoconnell.rollout;

import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class FeatureTest {

	@Test
	public void getGroups_returns_a_new_set() {
		Feature f = new Feature("chat");
		f.addGroup("all");

		Set<String> groups = f.getGroups();

		f.addGroup("admins");

		Assert.assertThat(groups, Matchers.contains("all"));
	}
}
