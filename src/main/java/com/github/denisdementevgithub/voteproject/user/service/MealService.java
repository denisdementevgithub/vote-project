package com.github.denisdementevgithub.voteproject.user.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.repository.datajpa.MealRepository;

import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFound;
import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkNotFoundForMenu;

@Service
public class MealService {

    private final MealRepository mealRepository;

    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    //@CacheEvict(value = "users", allEntries = true)
    public List<Meal> create(List<Meal> menu) {
        Assert.notNull(menu, "menu must not be null");
        return mealRepository.saveAll(menu);
    }

    public Meal create(Meal menu) {
        Assert.notNull(menu, "menu must not be null");
        return mealRepository.save(menu);
    }



    //@CacheEvict(value = "users", allEntries = true)
    public void delete(Restaurant restaurant) {
        getByRestaurant(restaurant);
        mealRepository.delete(restaurant);
    }

    public void delete(int id) {
        get(id);
        mealRepository.delete(id);
    }

    public Meal get(int id) {
        return checkNotFound(mealRepository.get(id), id);
    }

    public List<Meal> getByRestaurant(Restaurant restaurant) {
        return checkNotFoundForMenu(mealRepository.getByRestaurant(restaurant), restaurant);
    }


    @Cacheable("users")
    public List<Meal> getAll() {
        return mealRepository.getAll();
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(Meal meal) {
        Assert.notNull(meal, "user must not be null");
        mealRepository.save(meal);
    }


}
