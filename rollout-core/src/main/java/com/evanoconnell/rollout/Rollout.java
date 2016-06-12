package com.evanoconnell.rollout;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.evanoconnell.rollout.storage.IRolloutStorage;

public class Rollout<U extends IRolloutUser> {

	private IRolloutStorage storage;

	private Map<String,Predicate<U>> groups;

	public Rollout(IRolloutStorage storage) {
		this.storage = storage;
		groups = new HashMap<>();

		defineGroup("all", (user) -> user != null);
	}

	public void defineGroup(String group, Predicate<U> predicate) {
		groups.put(group, predicate);
	}

	public void activateGroup(String feature, String group) {
		withFeature(feature, (f) ->
			f.addGroup(group)
		);
	}

	public void deactivateGroup(String feature, String group) {
		withFeature(feature, (f) ->
			f.removeGroup(group)
		);
	}

	public boolean isActive(String feature, U user) {
		Feature f = get(feature);
		return f.isActive(this, user);
	}

	public boolean isActiveInGroup(String group, U user) {
		if (groups.containsKey(group)) {
			return groups.get(group).test(user);
		}
		return false;
	}

	Feature get(String feature) {
		Object value = storage.get(key(feature));
		return new Feature(feature, value);
	}

	private void save(Feature f) {
		storage.set(key(f.getName()), f.serialize());
	}

	private Object key(String feature) {
		return "feature:"+feature;
	}

	private void withFeature(String feature, Consumer<Feature> block) {
		Feature f = get(feature);
		block.accept(f);
		save(f);
	}

	public void activatePercentage(String feature, int percentage) {
		withFeature(feature, (f) ->
			f.setPercentage(percentage)
		);
	}

	public void deactivatePercentage(String feature) {
		activatePercentage(feature, 0);
	}

}
