package com.evanoconnell.rollout;

import org.hamcrest.Matcher;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ActiveFeature extends BaseMatcher<Feature> {

	private IRolloutUser user;

	public ActiveFeature(IRolloutUser user) {
		this.user = user;
	}

	public boolean matches(Object object) {
		if (object instanceof Feature) {
			Feature feature = (Feature) object;
			return feature.isActive(null, user);
		}
		return false;
	}

	public void describeTo(Description message) {
	}

	public static Matcher<Feature> activeFor(IRolloutUser user) {
		return new ActiveFeature(user);
	}

}
