package com.evanoconnell.rollout;

import java.util.HashSet;
import java.util.Set;

public class Feature {

	private String name;
	private Set<String> groups;

	public Feature(String name) {
		this.name = name;
		groups = new HashSet<>();
	}

	public Feature(String name, Object value) {
		this(name);

		if (value != null) {
			String[] groupNames = ((String) value).split(",");
			for (String g : groupNames) {
				groups.add(g);
			}
		}
	}


	public void addGroup(String group) {
		groups.add(group);
	}

	public boolean isActive(Rollout rollout, IRolloutUser user) {
		for (String group : groups) {
			boolean isActive = rollout.isActiveInGroup(group, user);
			if (isActive) {
				return true;
			}
		}

		return false;
	}

	public void removeGroup(String group) {
		groups.remove(group);
	}

	public Set<String> getGroups() {
		return new HashSet<>(groups);
	}

	public String getName() {
		return name;
	}
}
