package com.github.denisdementevgithub.voteproject.user.web.converter;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;

import javax.swing.text.html.Option;
import java.util.Optional;

public class MealUtils {

    public static Meal MealToToMeal(MealTo mealTo) {
        Optional<Integer> id = Optional.ofNullable(mealTo.getId());
        Meal meal = new Meal();
        if (id.isPresent()) {
            meal.setId(id.get());
        }
        meal.setName(mealTo.getName());
        meal.setPrice(mealTo.getPrice());
        Restaurant restaurant = new Restaurant();
        restaurant.setId(mealTo.getRestaurant_id());
        meal.setRestaurant(restaurant);
        return meal;
    }
}
