package com.github.denisdementevgithub.voteproject.user.service;

import lombok.Setter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.github.denisdementevgithub.voteproject.common.error.IllegalRequestDataException;
import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.repository.MealRepository;
import com.github.denisdementevgithub.voteproject.user.repository.RestaurantRepository;


import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFound;

@Service
@Setter
public class RestaurantService {
    private final RestaurantRepository crudRestaurantRepository;
    private final MealRepository crudMealRepository;
    private Clock clock;

    public RestaurantService(Clock clock, RestaurantRepository crudRestaurantRepository, MealRepository crudMealRepository) {
        this.crudRestaurantRepository = crudRestaurantRepository;
        this.crudMealRepository = crudMealRepository;
        this.clock = clock;
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
        LocalDate dateToday = LocalDate.now();
        if ((crudRestaurantRepository.get(id).getRegistered().toLocalDate()).equals(dateToday) &&
                (LocalTime.now(clock).isBefore(LocalTime.of(11,0)))) {
            crudRestaurantRepository.deleteVote(userId, dateToday);
            return crudRestaurantRepository.vote(id, userId);
        } else {
            throw new IllegalRequestDataException("Голосование для данной даты закрыто");
        }
    }

    public List<Restaurant> getAll() {
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
        Restaurant restaurant = crudRestaurantRepository.get(id);
        for (Meal meal : menu) {
            meal.setRestaurant(restaurant);
        }
        crudMealRepository.delete(restaurant);
        crudMealRepository.saveAll(menu);
    }

    public Restaurant getWithMenu(int id) {
        return crudRestaurantRepository.getWithMenu(id);
    }
}