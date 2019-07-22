# rollout (for Java)

[![Build Status](https://secure.travis-ci.org/eoconnell/rollout-java.svg)](http://travis-ci.org/eoconnell/rollout-java)
[![Coverage Status](https://coveralls.io/repos/github/eoconnell/rollout-java/badge.svg?branch=master)](https://coveralls.io/github/eoconnell/rollout-java?branch=master)

Feature flippers for Java. A port of James Golick's/FetLife's [rollout](https://github.com/FetLife/rollout).

Features marked with `TODO` are still being worked.

## Install it

With Maven:

```xml
<dependency>
	<groupId>com.evanoconnell</groupId>
	<artifactId>rollout-core</artifactId>
	<version>0.1.0-SNAPSHOT</version>
</dependency>
```

With Gradle:

```groovy
dependencies {
	compile 'com.evanoconnell:rollout-core:0.1.0-SNAPSHOT'
}
```

## Usage

Initialize a Rollout object.

```java
IRolloutStorage storage;
Rollout<IRolloutUser> rollout = new Rollout<>(storage);
```

You'll need to provide Rollout with your own storage, but that part is relatively simple. See the [relevant readme section](https://github.com/eoconnell/rollout-java#storage)

Check if a feature is active for a particular user:

```java
rollout.isActive("chat", user); // => true/false
```

You can activate features using a number of different mechanisms: percentages, groups and by the user's id.

## Groups

Rollout ships with one group by default: "all", which does exactly what it
sounds like.

You can activate the all group for the chat feature like this:

```java
rollout.activateGroup("chat", "all");
```

You might also want to define your own groups.

```java
rollout.defineGroup("admin", (User u) ->
  u.hasRole("admin")
);
```

You can activate multiple groups per feature.

Deactivate groups like this:

```java
rollout.deactivateGroup("chat", "all");
```

## Specific Users

You might want to let a specific user into a beta test or something. If that
user isn't part of an existing group, you can let them in specifically:

```java
rollout.activateUser("chat", user);
```

Deactivate them like this:

```java
rollout.deactivateUser("chat", user);
```

Note that user-specific activation relies on the `IRolloutUser` interface.

## User Percentages

If you're rolling out a new feature, you might want to test the waters by
slowly enabling it for a percentage of your users.

```java
rollout.activatePercentage("chat", 20);
```

The algorithm for determining which users get let in is this:

```java
// Feature.java
private boolean userInPercentage(long id) {
    return crc32(id) % 100 < percentage;
}
```

So for 20%, roughly 20% of your users should be let in. This also relies on the `IRolloutUser` interface.

Deactivate all percentages like this:

```java
rollout.deactivatePercentage("chat");
```

which is the same as:

```java
rollout.activatePercentage("chat", 0);
```

_Note that activating a feature for 100% of users will also make it active
"globally". That is when calling Rollout#isActive without a user object._

## Global actions (WIP)

While groups can come in handy, the actual global setter for a feature does not require a group to be passed.

```java
// TODO
rollout.activate("chat");
```

In that case you can check the global availability of a feature using the following

```java
// TODO
rollout.isActive("chat");
```

And if something is wrong you can set a feature off for everybody using

Deactivate everybody at once:

```java
// TODO
rollout.deactivate("chat");
```

## Storage

You'll have to implement your own storage backend that implements `IRolloutStorage`. The storage API has two functions, `read` and `write`, both of which should be simple enough to implement.

The `rollout-storage-local` module provides an in memory store (`MapStorage`) that you can look at for an example.

You can test that your implementation conforms to the storage contract by writing a JUnit test that extends `StorageContractTest`. See `MapStorageTest.java` for an example.

## License

Copyright © 2016 Evan O'Connell

Distributed under the MIT license
