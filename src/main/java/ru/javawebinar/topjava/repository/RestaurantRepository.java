package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.to.RestaurantTo;

import java.util.List;

public interface RestaurantRepository {

    Restaurant save(Restaurant restaurant);

    boolean delete(int id);

    Restaurant get(int id);

    List<RestaurantTo> getAll();

    List<RestaurantTo> getAllForToday();

    int vote(int id, int userId);
}
