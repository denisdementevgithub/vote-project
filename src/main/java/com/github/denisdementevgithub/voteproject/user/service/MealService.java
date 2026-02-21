package com.github.denisdementevgithub.voteproject.user.service;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.repository.MealRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFound;
import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFoundForMenu;

@Service
public class MealService {
    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public List<Meal> createAll(List<Meal> menu) {
        Assert.notNull(menu, "list of menu must not be null");
        return repository.saveAll(menu);
    }

    public Meal create(Meal menu) {
        Assert.notNull(menu, "menu must not be null");
        return repository.save(menu);
    }

    public void deleteByRestaurant(Restaurant restaurant) {
        getByRestaurant(restaurant);
        repository.deleteByRestaurant(restaurant);
    }

    public void deleteById(int id) {
        getById(id);
        repository.deleteById(id);
    }

    public Meal getById(int id) {
        return checkNotFound(repository.getById(id), id);
    }

    public List<Meal> getByRestaurant(Restaurant restaurant) {
        return checkNotFoundForMenu(repository.getByRestaurant(restaurant), restaurant);
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public void update(Meal meal) {
        Assert.notNull(meal, "meal must not be null");
        checkNotFound(repository.save(meal), meal.id());
    }

}
