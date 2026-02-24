package com.github.denisdementevgithub.voteproject.user.web.restaurant;

import com.github.denisdementevgithub.voteproject.user.model.Vote;
import com.github.denisdementevgithub.voteproject.user.service.MealService;
import com.github.denisdementevgithub.voteproject.user.service.VoteService;
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

    @Autowired
    private MealService mealService;

    @Autowired
    private VoteService voteService;

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<RestaurantTo> getAllForTodayWithMenu() {
        log.info("getAllForTodayWithMenu");
        return RestaurantUtils.listOfRestaurantsToRestaurantTos(service.getAllForTodayWithMenu(), voteService.getVotesForToday());
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Restaurant getWithMenu(int id) {
        log.info("getWithMenu {}", id);
        return service.getWithMenu(id);
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

    public void revote(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("revote {}", id);
        service.revote(id, userId);
    }


/*
    public List<RestaurantTo> getAllForToday() {
        log.info("getAllForToday");
        return RestaurantUtils.listOfRestaurantsToRestaurantTos(service.getAllForToday(), service.getVotes());

    }
*/






    /*
    public void setMenu(List<Meal> menu, int id) {
        log.info("setMenu menu {}", id);
        service.setMenu(menu, id);
    }

     */


}