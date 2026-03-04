package com.github.denisdementevgithub.voteproject.user.service;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.repository.MealRepository;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFound;

@Service
public class MealService {
    private final MealRepository repository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<MealTo> getAll() {
        return repository.getAll();
    }

    @Cacheable("getAllMealsForToday")
    public List<Meal> getAllForToday() {
        log.info("getAllForToday");
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atStartOfDay();
        LocalDateTime endDateTime = today.atTime(LocalTime.MAX);
        return repository.getAllForToday(startDateTime, endDateTime);
    }

    public Meal get(int id) {
        return checkNotFound(repository.get(id), id);
    }

    @CacheEvict(value = {"getAllMealsForToday", "getAllForTodayWithMenu"}, allEntries = true)
    public Meal create(Meal meal) {
        Assert.notNull(meal, "menu must not be null");
        return repository.save(meal);
    }

    @CacheEvict(value = {"getAllMealsForToday", "getAllForTodayWithMenu"}, allEntries = true)
    public void update(Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFound(repository.save(meal), meal.id());
    }

    @Transactional
    @CacheEvict(value = {"getAllMealsForToday", "getAllForTodayWithMenu"}, allEntries = true)
    public void delete(int id) {
        get(id);
        repository.delete(id);
    }
}
