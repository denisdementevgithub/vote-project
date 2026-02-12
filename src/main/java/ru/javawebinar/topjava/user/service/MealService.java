package ru.javawebinar.topjava.user.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.app.AuthorizedUser;
import ru.javawebinar.topjava.user.model.Meal;
import ru.javawebinar.topjava.user.model.Restaurant;
import ru.javawebinar.topjava.user.model.User;
import ru.javawebinar.topjava.user.repository.datajpa.MealRepository;
import ru.javawebinar.topjava.user.repository.datajpa.UserRepository;
import ru.javawebinar.topjava.user.to.UserTo;
import ru.javawebinar.topjava.user.util.UsersUtil;

import java.util.List;

import static ru.javawebinar.topjava.common.validation.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.common.validation.ValidationUtil.checkNotFoundForMenu;
import static ru.javawebinar.topjava.user.util.UsersUtil.prepareToSave;

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
