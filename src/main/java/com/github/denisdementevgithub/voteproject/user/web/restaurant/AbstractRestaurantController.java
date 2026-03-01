package com.github.denisdementevgithub.voteproject.user.web.restaurant;

import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.service.RestaurantService;
import com.github.denisdementevgithub.voteproject.user.web.SecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.assureIdConsistent;
import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkIsNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<Restaurant> getAllForTodayWithMenu() {
        log.info("getAllForTodayWithMenu");
        return service.getAllForTodayWithMenu();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkIsNew(restaurant);
        return service.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {}", restaurant);
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void vote(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("vote {}", id);
        service.vote(id, userId);
    }

    public void reVote(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("reVote {}", id);
        service.reVote(id, userId);
    }
}