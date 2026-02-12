package ru.javawebinar.topjava.user.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.user.model.Meal;
import ru.javawebinar.topjava.user.model.Restaurant;
import ru.javawebinar.topjava.user.repository.datajpa.MealRepository;
import ru.javawebinar.topjava.user.repository.datajpa.RestaurantRepository;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.javawebinar.topjava.common.validation.ValidationUtil.checkNotFound;

@Service
public class RestaurantService {
    private final RestaurantRepository crudRestaurantRepository;
    private final MealRepository crudMealRepository;

    public RestaurantService(RestaurantRepository crudRestaurantRepository, MealRepository crudMealRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudMealRepository = crudMealRepository;
    }

    public Restaurant get(int id) {
        return checkNotFound(crudRestaurantRepository.get(id), id);
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public void delete(int id) {
        get(id);
        checkNotFound(crudRestaurantRepository.delete(id), id);
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public int vote(int id, int userId) {
        LocalDate date = LocalDate.now();
        crudRestaurantRepository.deleteVote(userId, date);
        return crudRestaurantRepository.vote(id, userId);
    }

    public List<Restaurant> getAll() {
        /*List<RestaurantTo> restaurantsWithoutMenu =
                convertFromObjectsToRestaurantTos(crudRestaurantRepository.getAll());
        System.out.println(restaurantsWithoutMenu);
        for (RestaurantTo restaurantTo : restaurantsWithoutMenu) {
            List<Meal> menu = crudMealRepository.getAllForRestaurant(restaurantTo.id());
            restaurantTo.setMenu(menu);
        }
        System.out.println(restaurantsWithoutMenu);
        return restaurantsWithoutMenu;

         */
        return crudRestaurantRepository.getAll();
    }

    @Cacheable("restaurantTosForToday")
    public List<Restaurant> getAllForToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        return crudRestaurantRepository.getAllForToday(startDateTime, endDateTime);
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFound(crudRestaurantRepository.save(restaurant), restaurant.id());
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return crudRestaurantRepository.save(restaurant);
    }

    public Map<Integer, Integer> getVotes() {
        List<Object[]> listOfObject = crudRestaurantRepository.getVotes();
        Map<Integer, Integer> mapOfVotes = new HashMap<>();
        for (Object[] row: listOfObject) {
            int id = (Integer) row[0];
            int count = (Integer) row[1];
            mapOfVotes.put(id, count);
        }
        return mapOfVotes;
    }

    public void setMenu(List<Meal> menu, int id) {
        for (Meal meal : menu) {
            meal.setRestaurant(crudRestaurantRepository.get(id));
        }
        crudMealRepository.saveAll(menu);
    }

    public Restaurant getWithMenu(int id) {
        return crudRestaurantRepository.getWithMenu(id);
    }

}