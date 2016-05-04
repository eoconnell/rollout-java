package com.evanoconnell.rollout;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

public class Feature {

	private String name;
	private Set<String> groups;
	private int percentage;
	private CRC32 crc = new CRC32();

	public Feature(String name) {
		this.name = name;
		groups = new HashSet<>();
	}

	public Feature(String name, Object serializedValue) {
		this(name);

		if (serializedValue != null) {
			deserialize(serializedValue.toString());
		}
	}

	public void addGroup(String group) {
		groups.add(group);
	}

	public <U extends IRolloutUser> boolean isActive(Rollout<U> rollout, U user) {
		if (user !=null && userInPercentage(user.getId())) {
			return true;
		}
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

	public void setPercentage(int newPercentage) {
		percentage = newPercentage;
	}

	public Object getPercentage() {
		return percentage;
	}

	public Object serialize() {
		return String.format("%s|%s", percentage, groups.stream().collect(Collectors.joining(",")));
	}

	private boolean userInPercentage(long id) {
		return crc32(id) % 100 < percentage;
	}

	private long crc32(long i) {
		byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(i).array();
		crc.update(bytes);
		long value = crc.getValue();
		crc.reset();
		return value;
	}

	private void deserialize(String serializedValue) {
		String[] parts = serializedValue.split("\\|");
		String p = parts[0];
		if (!p.isEmpty()) {
			percentage = Integer.parseInt(parts[0]);
		}
		if (parts.length > 1) {
			String[] groupNames = parts[1].split(",");
			for (String g : groupNames) {
				groups.add(g);
			}
		}
	}
}
