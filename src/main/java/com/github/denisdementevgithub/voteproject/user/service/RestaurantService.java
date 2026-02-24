package com.github.denisdementevgithub.voteproject.user.service;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Vote;
import com.github.denisdementevgithub.voteproject.user.to.RestaurantTo;
import lombok.Setter;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.github.denisdementevgithub.voteproject.common.error.IllegalRequestDataException;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.repository.RestaurantRepository;


import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFound;

@Service
@Setter
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final MealService mealService;
    private final VoteService voteService;
    private Clock clock;

    public RestaurantService(Clock clock, RestaurantRepository restaurantRepository, MealService mealService, VoteService voteService) {
        this.restaurantRepository = restaurantRepository;
        this.mealService = mealService;
        this.voteService = voteService;
        this.clock = clock;
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }

    public List<Restaurant> getAllForTodayWithMenu() {
        Map<Restaurant, List<Meal>> map = mealService.getAllForToday().stream()
                .collect(Collectors.groupingBy(Meal::getRestaurant));
        map.entrySet().stream()
                .forEach(rest->{
                    rest.getKey().setMeals(rest.getValue());
                });
        return map.keySet().stream().toList();
    }


    public Restaurant get(int id) {
        return checkNotFound(restaurantRepository.get(id), id);
    }

    public Restaurant getWithMenu(int id) {
        return restaurantRepository.getWithMeals(id);
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFound(restaurantRepository.save(restaurant), restaurant.id());
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public void delete(int id) {
        get(id);
        checkNotFound(restaurantRepository.delete(id), id);
    }

    //@CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public int vote(int id, int userId) {
        List<Meal> meals = mealService.getMealsForRestaurantForToday(id);
        if (!meals.isEmpty() &&
                (LocalTime.now(clock).isBefore(LocalTime.of(23,0)))) {
            List<Vote> votesForToday = voteService.getVotesForTodayForUser(userId);
            if (votesForToday.isEmpty()) {
                return voteService.vote(id, userId);
            } else {
                throw new IllegalRequestDataException("Вы уже голосовали");
            }
            //crudMealRepository.deleteVote(userId, dateToday);
            //return crudMealRepository.vote(id, userId);
        } else {
            throw new IllegalRequestDataException("Голосование для данной даты закрыто");
        }
    }

    public int revote(int id, int userId) {
        List<Meal> meals = mealService.getMealsForRestaurantForToday(id);
        if (!meals.isEmpty() &&
                (LocalTime.now(clock).isBefore(LocalTime.of(23,0)))) {
            return voteService.revote(id, userId);

            //crudMealRepository.deleteVote(userId, dateToday);
            //return crudMealRepository.vote(id, userId);
        } else {
            throw new IllegalRequestDataException("Голосование для данной даты закрыто");
        }
    }






/*
    @Cacheable("restaurantTosForToday")
    public List<Restaurant> getAllForToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        return crudRestaurantRepository.getAllForToday(startDateTime, endDateTime);
    }
*/



/*
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

 */
/*
    public void setMenu(List<Meal> menu, int id) {
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        for (Meal meal : menu) {
            meal.setRestaurant(restaurant);
        }
        crudMealRepository.deleteByRestaurant(restaurant);
        crudMealRepository.saveAll(menu);
    }
*/


}