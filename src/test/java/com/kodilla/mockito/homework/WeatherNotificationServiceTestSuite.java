package com.kodilla.mockito.homework;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class WeatherNotificationServiceTest {

    WeatherNotificationService service;
    Notification notification;
    User user1;
    User user2;
    User user3;
    Location location1;
    Location location2;
    Location location3;

    @BeforeEach
    public void setUp() {
        service = new WeatherNotificationService();
        notification = Mockito.mock(Notification.class);
        user1 = Mockito.mock(User.class);
        user2 = Mockito.mock(User.class);
        user3 = Mockito.mock(User.class);
        location1 = Mockito.mock(Location.class);
        location2 = Mockito.mock(Location.class);
        location3 = Mockito.mock(Location.class);

        service.addUserToSubscriptionList(user1, location1);
        service.addUserToSubscriptionList(user2, location2);
    }

    @Test
    public void shouldAddUserToLocation() {
        service.sendNotificationToLocation(notification, location1);

        Mockito.verify(user1).receive(notification);
    }

    @Test
    public void shouldRemoveUserFromLocation() {
        service.removeUserFromSubscriptionList(user1, location1);
        service.sendNotificationToLocation(notification, location1);

        Mockito.verify(user1, Mockito.never()).receive(notification);
    }

    @Test
    public void shouldRemoveUserFromAllLocations() {
        service.addUserToSubscriptionList(user1, location2);

        service.removeUserFromAllSubscriptions(user1);

        service.sendNotificationToLocation(notification, location1);
        service.sendNotificationToLocation(notification, location2);

        Mockito.verify(user1, Mockito.never()).receive(notification);
    }

    @Test
    public void shouldSendNotificationToAllUniqueUsers() {
        service.addUserToSubscriptionList(user2, location1);
        service.addUserToSubscriptionList(user1, location2); // user1 zapisany 2 razy

        service.sendNotificationToAllUsers(notification);

        Mockito.verify(user1, Mockito.times(1)).receive(notification);
        Mockito.verify(user2, Mockito.times(1)).receive(notification);
    }

    @Test
    public void shouldSendNotificationOnlyToUsersInGivenLocation() {
        service.sendNotificationToLocation(notification, location1);

        Mockito.verify(user1, Mockito.times(1)).receive(notification);
        Mockito.verify(user2, Mockito.never()).receive(notification);
    }

    @Test
    public void shouldRemoveLocation() {
        service.removeLocation(location1);

        service.sendNotificationToLocation(notification, location1);

        Mockito.verify(user1, Mockito.never()).receive(notification);
    }

    // DODANE NOWE TESTY:

    @Test
    public void shouldNotDuplicateSubscriptionForSameUserAndLocation(){
        service.addUserToSubscriptionList(user1, location1); // ponowne dodanie user1 do location1, pierwsze było w BeforeEach

        service.sendNotificationToLocation(notification, location1);

        Mockito.verify(user1, Mockito.times(1)).receive(notification);
    }

    @Test
    public void shouldSendToAllUsersInSameLocationOnceEach(){
        service.addUserToSubscriptionList(user3, location1); // oprócz user3 w location1 jest już user1, który był dodany w BeforeEach

        service.sendNotificationToLocation(notification, location1);

        Mockito.verify(user1, Mockito.times(1)).receive(notification);
        Mockito.verify(user3, Mockito.times(1)).receive(notification);
    }

    @Test
    public void unsubscribeFromOneLocationKeepsOtherSubscriptionsIntact(){
        service.addUserToSubscriptionList(user1, location2); // user1 był z BeforeEach tylko w location1, a teraz dodajemy go też do location2

        service.removeUserFromSubscriptionList(user1, location1);

        service.sendNotificationToLocation(notification, location2);

        Mockito.verify(user1, Mockito.times(1)).receive(notification);
    }

    @Test
    public void removingNonexistentSubscriptionIsIdempotent() {

        service.removeUserFromSubscriptionList(user1, location1); // pierwsze usunięcie pary user1 i location1

        assertDoesNotThrow(() -> service.removeUserFromSubscriptionList(user1, location1)); // test czy ponowne usunięcie tej samej pary rzuci wyjątek

        service.sendNotificationToAllUsers(notification); // sprawdzenie czy system i tak działą

        Mockito.verify(user2, Mockito.times(1)).receive(notification); // user2 powinien mimo wszystko dostac powiadomienie
        Mockito.verify(user1, Mockito.never()).receive(notification); // user1 nie powinien dostać nic
    }

    @Test
    public void canResubscribeAfterRemovingAllSubscriptions(){
        service.addUserToSubscriptionList(user1, location2);
        service.removeUserFromAllSubscriptions(user1);
        service.addUserToSubscriptionList(user1, location1);
        service.addUserToSubscriptionList(user1, location2);
        service.sendNotificationToLocation(notification, location1);
        service.sendNotificationToLocation(notification, location2);

        Mockito.verify(user1, Mockito.times(2)).receive(notification);
    }

    @Test
    public void removingLocationDoesNotAffectOtherLocations(){
        service.removeLocation(location1);
        service.sendNotificationToLocation(notification, location2);

        Mockito.verify(user2, Mockito.times(1)).receive(notification);
        Mockito.verify(user1, Mockito.never()).receive(notification);
    }

    @Test
    public void sendingToEmptyLocationDoesNothing(){
        service.sendNotificationToLocation(notification, location3);

        Mockito.verify(user1, Mockito.never()).receive(notification);
        Mockito.verify(user2, Mockito.never()).receive(notification);
        Mockito.verify(user3, Mockito.never()).receive(notification);
    }

    @Test
    public void sendToAllWhenNoSubscribersDoesNothing(){
        service.removeLocation(location1);
        service.removeLocation(location2); // zostanie tylko location3, które nie ma i tak żadnego usera dodanego

        service.sendNotificationToAllUsers(notification);

        Mockito.verify(user1, Mockito.never()).receive(notification);
        Mockito.verify(user2, Mockito.never()).receive(notification);
        Mockito.verify(user3, Mockito.never()).receive(notification);
    }

    @Test
    public void sendsTheExactNotificationInstance(){
        service.sendNotificationToLocation(notification, location1);
        service.sendNotificationToLocation(notification, location2);

        Mockito.verify(user1).receive(Mockito.same(notification));
        Mockito.verify(user2).receive(Mockito.same(notification));
    }
}
