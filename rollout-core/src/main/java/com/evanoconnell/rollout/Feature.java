package com.evanoconnell.rollout;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.zip.CRC32;

public class Feature {

	private String name;
	private Set<String> groups;
	private Set<Long> users;
	private int percentage;
	private CRC32 crc = new CRC32();

	public Feature(String name) {
		this.name = name;
		groups = new HashSet<>();
		users = new HashSet<>();
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

	public void addUser(long userId) {
		users.add(userId);
	}

	public void removeUser(long userId) {
		users.remove(userId);
	}

	public <U extends IRolloutUser> boolean isActive(Rollout<U> rollout, U user) {
		if (user !=null && userInPercentage(user.getId())) {
			return true;
		}
		if (user !=null && userInActiveUsers(user.getId())) {
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

	public Set<Long> getUsers() {
		return new HashSet<>(users);
	}

	public String getName() {
		return name;
	}

	public void setPercentage(int newPercentage) {
		percentage = newPercentage;
	}

	public String serialize() {
		return String.format("%s|%s|%s", 
			percentage,
			users.stream().map(i -> Long.toString(i)).collect(Collectors.joining(",")),
			groups.stream().collect(Collectors.joining(","))
		);
	}

	int getPercentage() {
		return percentage;
	}

	private boolean userInPercentage(long id) {
		return crc32(id) % 100 < percentage;
	}

	private boolean userInActiveUsers(long id) {
		return users.contains(id);
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
			String[] userIds = parts[1].split(",");
			for (String u : userIds) {
				if (!u.isEmpty()) {
					users.add(Long.valueOf(u));
				}
			}
		}
		if (parts.length > 2) {
			String[] groupNames = parts[2].split(",");
			for (String g : groupNames) {
				if (!g.isEmpty()) {
					groups.add(g);
				}
			}
		}
	}
}
