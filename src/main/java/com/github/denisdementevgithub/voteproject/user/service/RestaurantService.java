package com.github.denisdementevgithub.voteproject.user.service;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.model.User;
import com.github.denisdementevgithub.voteproject.user.model.Vote;
import com.github.denisdementevgithub.voteproject.user.repository.RestaurantRepository;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFound;

@Service
@Setter
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MealService mealService;
    private final VoteService voteService;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public RestaurantService(RestaurantRepository restaurantRepository, MealService mealService, VoteService voteService) {
        this.restaurantRepository = restaurantRepository;
        this.mealService = mealService;
        this.voteService = voteService;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    @Cacheable("getAllForTodayWithMenu")
    public List<Restaurant> getAllForTodayWithMenu() {
        log.info("getAllForToday");
        Map<Restaurant, List<Meal>> map = mealService.getAllForToday().stream()
                .collect(Collectors.groupingBy(Meal::getRestaurant));
        map.forEach(Restaurant::setMeals);
        return map.keySet().stream().sorted(Comparator.comparingInt(Restaurant::getId)).toList();
    }

    public Restaurant get(int id) {
        return checkNotFound(restaurantRepository.get(id), id);
    }

    @CacheEvict(value = "getAllForTodayWithMenu", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "getAllForTodayWithMenu", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFound(restaurantRepository.save(restaurant), restaurant.id());
    }

    @Transactional
    @CacheEvict(value = "getAllForTodayWithMenu", allEntries = true)
    public void delete(int id) {
        get(id);
        checkNotFound(restaurantRepository.delete(id), id);
    }

    public Vote vote(int restaurantId, int userId) {
        return voteService.save(new Vote(getFakeRestaurant(restaurantId), getFakeUser(userId)));
    }

    public Vote reVote(int restaurantId, int userId) {
        return voteService.update(new Vote(getFakeRestaurant(restaurantId), getFakeUser(userId)));
    }

    public static User getFakeUser(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }

    public static Restaurant getFakeRestaurant(int id) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return restaurant;
    }
}