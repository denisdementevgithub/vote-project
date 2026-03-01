package com.github.denisdementevgithub.voteproject.service;

import com.github.denisdementevgithub.voteproject.common.error.NotFoundException;
import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.service.MealService;
import com.github.denisdementevgithub.voteproject.user.service.RestaurantService;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.MealUtils;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.denisdementevgithub.voteproject.MealTestData.*;
import static com.github.denisdementevgithub.voteproject.RestaurantTestData.NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MealServiceTest extends AbstractServiceTest {

    @Autowired
    protected MealService service;

    @Test
    void delete() {
        service.delete(meal100011.id());
        assertThrows(NotFoundException.class, () -> service.get(meal100011.id()));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void create() {
        Meal created = service.create(MealUtils.MealToToMeal(getNew()));
        int newId = created.id();
        Meal newMeal = MealUtils.MealToToMeal(getNew());
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId), newMeal);
    }

    @Test
    void get() {
        Meal actual = service.get(meal100011.id());
        MEAL_MATCHER.assertMatch(actual, meal100011);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void update() {
        Meal updated = MealUtils.MealToToMeal(getUpdated());
        service.update(updated);
        MEAL_MATCHER.assertMatch(service.get(meal100011.id()), updated);
    }

    @Test
    void getAll() {
        MEAL_MATCHER.assertMatch(service.getAll(), meals);
    }

    @Test
    void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Meal(null, null, 0, LocalDateTime.now())));
    }
}
