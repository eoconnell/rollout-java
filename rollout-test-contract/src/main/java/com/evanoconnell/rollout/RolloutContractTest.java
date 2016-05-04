package com.evanoconnell.rollout;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public abstract class RolloutContractTest<U extends IRolloutUser> {

	private Rollout<U> rollout;

	/**
	 *
	 * @return
	 */
	public abstract Rollout<U> createNewRolloutWithNoActiveFeatures();

	public abstract U createNewRolloutUser(long id);

	@Before
	public void beforeEach() {
		rollout = createNewRolloutWithNoActiveFeatures();
	}

	/**
	 * The default 'all' group
	 */
	private void when_using_the_default_all_group() {
		rollout.activateGroup("chat", "all");
	}

	@Test
	public void the_default_all_group__is_active_for_every_user() {
		when_using_the_default_all_group();

		assertThat(rollout.isActive("chat", createNewRolloutUser(1)), is(true));
	}

	@Test
	public void the_default_all_group__is_not_active_for_null_user() {
		when_using_the_default_all_group();

		assertThat(rollout.isActive("chat", null), is(false));
	}

	/**
	 * Activating a group
	 */
	private void when_a_group_is_activated() {
		rollout.defineGroup("fivesonly", (user) ->
			user.getId() == 5
		);
	}

	@Test
	public void when_a_group_is_activated___the_feature_is_active_for_users_for_which_the_predicate_is_true() {
		when_a_group_is_activated();

		rollout.activateGroup("chat", "fivesonly");

		assertThat(rollout.isActive("chat", createNewRolloutUser(5)), is(true));
	}

	@Test
	public void when_a_group_is_activated___the_feature_is_not_active_for_users_for_which_the_predicate_is_false() {
		when_a_group_is_activated();

		rollout.activateGroup("chat", "fivesonly");

		assertThat(rollout.isActive("chat", createNewRolloutUser(10)), is(false));
	}

	/**
	 * Deactivating a group
	 */
	private void when_deactivating_a_group() {
		rollout.defineGroup("fivesonly", (user) -> user.getId() == 5);
		when_using_the_default_all_group();
		rollout.activateGroup("chat", "some");
		rollout.activateGroup("chat", "fivesonly");
		rollout.deactivateGroup("chat", "all");
		rollout.deactivateGroup("chat", "some");
	}

	@Test
	public void when_deactivating_a_group__it_deactivates_the_rule_for_that_group() {
		when_deactivating_a_group();

		assertThat(rollout.isActive("chat", createNewRolloutUser(10)), is(false));
		assertThat(rollout.isActive("chat", createNewRolloutUser(5)), is(true));
	}

	@Test
	public void when_deactivating_a_group__it_leaves_the_other_groups_active() {
		when_deactivating_a_group();

		assertThat(rollout.get("chat").getGroups(), contains("fivesonly"));
	}

	/**
	 * Percentage
	 */

	@Test
	public void when_activating_for_a_percentage__100_percent_is_on_for_all_and_0_percent_is_off_for_all() {
		rollout.activatePercentage("chat", 100);

		assertThat(rollout.isActive("chat", createNewRolloutUser(1)), is(true));

		rollout.activatePercentage("chat", 0);

		assertThat(rollout.isActive("chat", createNewRolloutUser(1)), is(false));
	}

	/**
	 * Activating features
	 */
	@Ignore("pending...")
	@Test
	public void activate_feature_off_then_on() {
	}

	@Ignore("pending...")
	@Test
	public void activate_feature_on_stays_on() {
	}
}
