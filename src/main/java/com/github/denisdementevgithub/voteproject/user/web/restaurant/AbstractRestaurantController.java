package com.github.denisdementevgithub.voteproject.user.web.restaurant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.service.RestaurantService;
import com.github.denisdementevgithub.voteproject.user.to.RestaurantTo;
import com.github.denisdementevgithub.voteproject.user.web.SecurityUtil;
import com.github.denisdementevgithub.voteproject.user.web.converter.RestaurantUtils;

import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.assureIdConsistent;
import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkIsNew;

public abstract class AbstractRestaurantController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RestaurantService service;

    public Restaurant get(int id) {
        log.info("get restaurant {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete restaurant {}", id);
        service.delete(id);
    }

    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return RestaurantUtils.listOfRestaurantsToRestaurantTos(service.getAll(), service.getVotes());
    }

    public List<RestaurantTo> getAllForToday() {
        log.info("getAllForToday");
        return RestaurantUtils.listOfRestaurantsToRestaurantTos(service.getAllForToday(), service.getVotes());

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

    public void vote(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("vote rest {}", id);
        service.vote(id, userId);
    }

    public void setMenu(List<Meal> menu, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("setMenu menu {}", id);
        service.setMenu(menu, id);
    }
    public Restaurant getWithMenu(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("getWithMenu {}", id);
        return service.getWithMenu(id);
    }

}