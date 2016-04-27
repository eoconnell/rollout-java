package com.evanoconnell.rollout.geode;


import com.gemstone.gemfire.distributed.ServerLauncher;

public class ServerDriver {

	public static void main(String[] args) {
		ServerLauncher launcher = new ServerLauncher.Builder()
				.set("start-locator", "localhost[10334]")
				.set("cache-xml-file", "src/test/resources/server-cache.xml")
				.build();

		launcher.start();

		System.out.println(launcher.status());
	}
}
