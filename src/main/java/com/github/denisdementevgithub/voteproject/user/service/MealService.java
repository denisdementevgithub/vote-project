package com.github.denisdementevgithub.voteproject.user.service;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Vote;
import com.github.denisdementevgithub.voteproject.user.repository.MealRepository;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.MealUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFound;
import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFoundForMenu;

@Service
public class MealService {
    private final MealRepository repository;
    private Clock clock;

    public MealService(MealRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public List<Meal> getAllForToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        return repository.getAllForToday(startDateTime, endDateTime);
    }

    public List<Meal> getMealsForRestaurantForToday(int restaurantId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        return repository.getMealsForRestaurantForToday(startDateTime, endDateTime, restaurantId);
    }



    public Meal get(int id) {
        return checkNotFound(repository.get(id), id);
    }

    public Meal create(Meal meals) {
        Assert.notNull(meals, "menu must not be null");
        return repository.save(meals);
    }

    public void update(Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFound(repository.save(meal), meal.id());
    }

    public void delete(int id) {
        get(id);
        repository.delete(id);
    }
/*
    public Map<Integer, Integer> getVotes() {
        List<Object[]> listOfObject = repository.getVotes();
        Map<Integer, Integer> mapOfVotes = new HashMap<>();
        for (Object[] row: listOfObject) {
            int id = (Integer) row[0];
            int count = (Integer) row[1];
            mapOfVotes.put(id, count);
        }
        return mapOfVotes;
    }

    public Map<Integer, Integer> getVotesForToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        List<Object[]> listOfObject = repository.getVotesForDate(startDateTime, endDateTime);
        Map<Integer, Integer> mapOfVotes = new HashMap<>();
        for (Object[] row: listOfObject) {
            int id = (Integer) row[0];
            int count = (Integer) row[1];
            mapOfVotes.put(id, count);
        }
        return mapOfVotes;
    }





    public Map<Integer, Integer> getVotesForToday() {
        LocalDate date = LocalDate.now();
        List<Object[]> listOfObject = repository.getVotesForToday(date);
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
    public int vote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        return repository.vote(restaurantId, userId, startDateTime, endDateTime);
    }
    public int revote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        repository.deleteVote(userId, today);
        return repository.vote(restaurantId, userId, startDateTime, endDateTime);
    }


     */



/*
    public List<Meal> createAll(List<Meal> meals) {
        Assert.notNull(meals, "list of menu must not be null");
        return repository.saveAll(meals);
    }
*/

/*
    public void deleteByRestaurant(Restaurant restaurant) {
        getByRestaurant(restaurant);
        repository.deleteByRestaurant(restaurant);
    }
*/



/*
    public List<Meal> getByRestaurant(Restaurant restaurant) {
        return checkNotFoundForMenu(repository.getByRestaurant(restaurant), restaurant);
    }
*/




}
