package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.repository.RestaurantRepository;
import ru.javawebinar.topjava.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;

@Service
public class RestaurantService {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public Restaurant get(int id) {
        return checkNotFound(repository.get(id), id);
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public void delete(int id) {
        checkNotFound(repository.delete(id), id);
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public int vote(int id, int userId) {return repository.vote(id, userId);}

    public List<RestaurantTo> getAll() {
        return repository.getAll();
    }

    @Cacheable("restaurantTosForToday")
    public List<RestaurantTo> getAllForToday() {
        log.debug("getAllForToday");
        return repository.getAllForToday();
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFound(repository.save(restaurant), restaurant.id());
    }

    @CacheEvict(value = "restaurantTosForToday", allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

}