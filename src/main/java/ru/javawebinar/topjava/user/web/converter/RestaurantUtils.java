package ru.javawebinar.topjava.user.web.converter;

import ru.javawebinar.topjava.user.model.Restaurant;
import ru.javawebinar.topjava.user.to.RestaurantTo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RestaurantUtils {
    public static List<RestaurantTo> listOfRestaurantsToRestaurantTos(List<Restaurant> restaurants, Map<Integer, Integer> mapOfVotes) {
        List<RestaurantTo> listOfRestaurantTo = new ArrayList<>();
        for (Restaurant restaurant:restaurants) {
            listOfRestaurantTo.add(restaurantToRestaurantTo(restaurant, mapOfVotes.get(restaurant.getId())));
        }
        return listOfRestaurantTo;
    }

    public static RestaurantTo restaurantToRestaurantTo(Restaurant restaurant, int numberOfVotes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getMenu(), numberOfVotes,restaurant.getRegistered());
    }
}
