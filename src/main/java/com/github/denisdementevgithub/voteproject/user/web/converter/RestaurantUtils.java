package com.github.denisdementevgithub.voteproject.user.web.converter;

import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.to.RestaurantTo;

import java.util.List;
import java.util.Map;

public class RestaurantUtils {

    public static List<RestaurantTo> listOfRestaurantsToRestaurantTos(List<Restaurant> restaurants, Map<Integer, Integer> mapOfVotes) {
        return restaurants.stream()
                .map(restaurant -> restaurantToRestaurantTo(restaurant, mapOfVotes.getOrDefault(restaurant.getId(), 0)))
                .toList();
    }

    public static RestaurantTo restaurantToRestaurantTo(Restaurant restaurant, int numberOfVotes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getMeals(), numberOfVotes, restaurant.getRegistered());
    }
}
