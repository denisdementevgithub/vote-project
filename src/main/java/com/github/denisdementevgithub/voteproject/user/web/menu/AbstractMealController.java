package com.github.denisdementevgithub.voteproject.user.web.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.service.MealService;

import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.assureIdConsistent;
import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.checkIsNew;

public abstract class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService mealService;

    public List<Meal> getAll() {
        log.info("getAll");
        return mealService.getAll();
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return mealService.get(id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        return mealService.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        mealService.delete(id);
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        mealService.update(meal);
    }
}
