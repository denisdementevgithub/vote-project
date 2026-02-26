package com.github.denisdementevgithub.voteproject.user.web.converter;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    public static MealTo mealToMealTo(Meal meal) {
        return new MealTo(meal.getId(), meal.getName(), meal.getPrice(), meal.getRestaurant().getId(), meal.getRegistered());
    }

    public static List<MealTo> listOfMealsToListOfMealTos(List<Meal> meals) {
        List<MealTo> listOfMealTo = new ArrayList<>();
        for (Meal meal:meals) {
            listOfMealTo.add(mealToMealTo(meal));
        }
        return listOfMealTo;
    }
}
