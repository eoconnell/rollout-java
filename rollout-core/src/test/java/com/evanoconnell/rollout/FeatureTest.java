package com.evanoconnell.rollout;

import static com.evanoconnell.rollout.ActiveFeature.activeFor;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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

  /**
   * Percentage Activation
   */

	@Test
	public void it_is_active_when_the_percentage_is_100() {
		Feature feature = new Feature("chat");
		feature.setPercentage(100);

		IRolloutUser user = new IRolloutUser() {
		  @Override
		  public long getId() {
		    return 1;
		  }
		};

		assertThat(feature, is(activeFor(user)));
	}

	@Test
	public void it_is_not_active_when_the_percentage_is_0() {
		Feature feature = new Feature("chat");
		feature.setPercentage(0);

		IRolloutUser user = new IRolloutUser() {
		  @Override
		  public long getId() {
		    return 1;
		  }
		};

		assertThat(feature, is(not(activeFor(user))));
	}

	/**
	 * User Activation
	 */

	@Test
	public void it_is_active_when_the_user_is_added() {
		Feature feature = new Feature("chat");
		feature.addUser(1);

		IRolloutUser user = new IRolloutUser() {
			@Override
			public long getId() {
				return 1;
			}
		};

		assertThat(feature, is(activeFor(user)));
	}

	@Test
	public void it_is_not_active_when_the_user_is_not_added() {
		Feature feature = new Feature("chat");
		feature.addUser(0);

		IRolloutUser user = new IRolloutUser() {
			@Override
			public long getId() {
				return 1;
			}
		};

		assertThat(feature, is(not(activeFor(user))));
	}

	/**
	 * User Deactivation
	 */

	@Test
	public void it_is_not_active_when_the_user_is_removed() {
		Feature feature = new Feature("chat");
		feature.addUser(1);
		feature.removeUser(1);

		IRolloutUser user = new IRolloutUser() {
			@Override
			public long getId() {
				return 1;
			}
		};

		assertThat(feature, is(not(activeFor(user))));
	}

	/**
	 * Serialization
	 */

	@Test
	public void serialize_default() {
		Feature f = new Feature("chat");
		assertThat(f.serialize(), is("0||"));
	}

	@Test
	public void serialize_with_percentage_users_and_groups() {
		Feature f = new Feature("chat");
		f.setPercentage(100);
		f.addUser(1);
		f.addUser(2);
		f.addGroup("alpha");
		f.addGroup("bravo");

		assertThat(f.serialize(), is("100|1,2|bravo,alpha"));
	}

  /**
   * Deserialization
   */

	@Test
	public void it_can_be_constructed_from_the_serialized_value() {
		Feature f = new Feature("chat", "100|32,4|bravo,alpha");

		assertThat(f.getPercentage(), is(100));
		assertThat(f.getUsers(), containsInAnyOrder(32L, 4L));
		assertThat(f.getGroups(), containsInAnyOrder("alpha", "bravo"));
	}

	@Test
	public void it_can_be_constructed_from_the_serialized_value_with_no_groups() {
		Feature f = new Feature("chat", "100||");

		assertThat(f.getPercentage(), is(100));
		assertThat(f.getGroups(), is(empty()));
	}

}
