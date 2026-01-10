package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.RestaurantTestData;
import ru.javawebinar.topjava.RestaurantUsersTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.model.RestaurantUsers;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.RestaurantRepository;
import ru.javawebinar.topjava.to.RestaurantTo;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static ru.javawebinar.topjava.RestaurantTestData.restaurants;

@Repository
public class InMemoryRestaurantRepository implements RestaurantRepository{
    private static final Logger log = LoggerFactory.getLogger(InMemoryRestaurantRepository.class);

    List<RestaurantTo> restaurantTos = new ArrayList<>();

    {
        restaurantTos = RestaurantTestData.getTosFromRest(RestaurantTestData.restaurants);
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public Restaurant save(Restaurant restaurant) {
        restaurants.add(restaurant);
        return restaurants.get(restaurant.getId());
    }

    @Override
    public boolean delete(int id) {
        return restaurants.remove(id) != null;
    }

    @Override
    public Restaurant get(int id) {
        RestaurantTo restaurantTo =  restaurantTos.stream()
                .filter(restTo -> restTo.getId() == id)
                .toList().getFirst();
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName(), restaurantTo.getMenu(), restaurantTo.getRegistered());
    }

    @Override
    public List<RestaurantTo> getAll() {
        return RestaurantTestData.restaurantTos.stream()
                .sorted(Comparator.comparing(RestaurantTo::getId))
                .toList();
    }

    @Override
    public List<RestaurantTo> getAllForToday() {
        return restaurantTos.stream()
                .filter(restTo -> restTo.getRegistered().equals(LocalDate.now()))
                .sorted(Comparator.comparing(RestaurantTo::getId))
                .toList();
    }

    @Override
    @Transactional(propagation = Propagation.NEVER)
    public int vote(int id, int userId) {
        User user = new User();
        user.setId(userId);
        return RestaurantUsersTestData.restaurantUsersList.add(new RestaurantUsers(100016, get(id), user, LocalDate.now())) ? 1 : 0;

    }
}