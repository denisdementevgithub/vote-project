package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.to.RestaurantTo;

import java.util.List;

public interface RestaurantRepository {
    // null if updated restaurant does not belong to userId
    Restaurant save(Restaurant restaurant);

    // false if restaurant does not belong to userId
    boolean delete(int id);

    // null if restaurant does not belong to userId
    Restaurant get(int id);

    // ORDERED dateTime desc
    List<RestaurantTo> getAll();

    List<RestaurantTo> getAllForToday();

    int vote(int id, int userId);

    // ORDERED dateTime desc
    //List<Restaurant> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    //default Restaurant getWithUser(int id) {
      //  throw new UnsupportedOperationException();
    //}
}
