package com.kodilla.mockito.homework;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WeatherNotificationService {

    Map<Location, Set<User>> subscriptions = new HashMap<>();

    public void addUserToSubscriptionList(User user, Location location) {
        subscriptions
                .computeIfAbsent(location, loc -> new HashSet<>())
                .add(user);
    }

    public void removeUserFromSubscriptionList(User user, Location location) {
        if (subscriptions.containsKey(location)) {
            Set<User> locationSubscribers = subscriptions.get(location);
            locationSubscribers.remove(user);
            if (locationSubscribers.isEmpty()) {
                subscriptions.remove(location);
            }
        }
    }

    public void removeUserFromAllSubscriptions(User user) {
        subscriptions.entrySet().removeIf(entry -> {
            Set<User> locationSubscribers = entry.getValue();
            locationSubscribers.remove(user);
            return locationSubscribers.isEmpty();
        });
    }

    public void sendNotificationToAllUsers(Notification notification) {
        Set<User> allUsers = new HashSet<>();
        for (Set<User> locationSubscribers : subscriptions.values()) {
            allUsers.addAll(locationSubscribers);
        }
        for (User user : allUsers) {
            user.receive(notification);
        }
    }

    public void sendNotificationToLocation(Notification notification, Location location) {
        Set<User> subscribers = subscriptions.get(location);
        if (subscribers != null) {
            for (User user : subscribers) {
                user.receive(notification);
            }
        }
    }

    public void removeLocation(Location location) {
        subscriptions.remove(location);
    }
}
