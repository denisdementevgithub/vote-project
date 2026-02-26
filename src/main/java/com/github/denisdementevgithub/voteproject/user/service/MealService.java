package com.github.denisdementevgithub.voteproject.user.service;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.repository.MealRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFound;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public List<Meal> getAllForToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atStartOfDay();
        LocalDateTime endDateTime = today.atTime(LocalTime.MAX);
        return repository.getAllForToday(startDateTime, endDateTime);
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
}
