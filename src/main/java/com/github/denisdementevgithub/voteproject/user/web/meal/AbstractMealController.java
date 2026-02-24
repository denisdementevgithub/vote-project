package com.github.denisdementevgithub.voteproject.user.web.meal;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.service.MealService;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.MealUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.assureIdConsistent;
import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkIsNew;

public abstract class AbstractMealController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public List<Meal> getAllForToday() {
        log.info("getAllForToday");
        return service.getAllForToday();
    }

/*
    public List<MealTo> getMealsForRestaurantForToday(int restaurantId) {
        log.info("getMealsForRestaurantForToday");
        return MealUtils.listOfMealsToListOfMealTos(service.getMealsForRestaurantForToday(restaurantId), service.getVotes());
    }

 */

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return service.create(meal);
    }

    public void update(Meal meal, int id) {
        log.info("update {}", meal);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }







    /*
    public Restaurant getWithMenu(int id) {
        log.info("getWithMenu {}", id);
        return service.getWithMenu(id);
    }
*/
}
