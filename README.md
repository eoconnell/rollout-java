# rollout (for Java)

Feature flippers for Java. A port of James Golick's/FetLife's [rollout](https://github.com/FetLife/rollout).

Features marked with `TODO` are still being worked.

## Install it

With Maven:

```xml
<dependency>
	<groupId>com.evanoconnell</groupId>
	<artifactId>rollout-core</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
<dependency>
	<groupId>com.evanoconnell</groupId>
	<artifactId>rollout-storage</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

## How it works

Initialize a rollout object.

```java
IRolloutStorage storage = new MapStorage();
Rollout<User> rollout = new Rollout(storage);
```

Check whether a feature is active for a particular user:

```java
rollout.isActive("chat", user); // => true/false
```

Check whether a feature is active globally:

```java
// TODO
rollout.isActive("chat");
```

You can activate features using a number of different mechanisms.

## Groups

Rollout ships with one group by default: "all", which does exactly what it
sounds like.

You can activate the all group for the chat feature like this:

```java
rollout.activateGroup("chat", "all");
```

You might also want to define your own groups. We have one for our caretakers:

```java
rollout.defineGroup("caretakers", (User u) ->
  u.isCaretaker()
);
```

You can activate multiple groups per feature.

Deactivate groups like this:

```java
rollout.deactivateGroup("chat", "all");
```

## Specific Users (WIP)

You might want to let a specific user into a beta test or something. If that
user isn't part of an existing group, you can let them in specifically:

```java
// TODO
rollout.activateUser("chat", user);
```

Deactivate them like this:

```java
// TODO
rollout.deactivateUser("chat", user);
```

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

So, for 20%, users 0, 1, 10, 11, 20, 21, etc would be allowed in. Those users
would remain in as the percentage increases.

Deactivate all percentages like this:

```java
rollout.deactivatePercentage("chat");
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

## License

Copyright © 2016 Evan O'Connell

Distributed under the MIT license
