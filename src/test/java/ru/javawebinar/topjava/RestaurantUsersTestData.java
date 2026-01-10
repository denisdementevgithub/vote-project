package ru.javawebinar.topjava;

import jdk.dynalink.linker.LinkerServices;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.model.RestaurantUsers;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.RestaurantTestData.restaurant1;

public class RestaurantUsersTestData {

    public static final RestaurantUsers restaurantUsers1 = new RestaurantUsers(100011,RestaurantTestData.restaurant2, UserTestData.user, LocalDate.now());
    public static final RestaurantUsers restaurantUsers2 = new RestaurantUsers(100012,RestaurantTestData.restaurant3, UserTestData.user1, LocalDate.now());
    public static final RestaurantUsers restaurantUsers3 = new RestaurantUsers(100013,RestaurantTestData.restaurant4, UserTestData.user1, LocalDate.now());
    public static final RestaurantUsers restaurantUsers4 = new RestaurantUsers(100014,RestaurantTestData.restaurant5, UserTestData.user1, LocalDate.now());
    public static final RestaurantUsers restaurantUsers5 = new RestaurantUsers(100015,RestaurantTestData.restaurant6, UserTestData.admin, LocalDate.now());

    public static final List<RestaurantUsers> restaurantUsersList = List.of(restaurantUsers1, restaurantUsers2, restaurantUsers3, restaurantUsers4, restaurantUsers5);
}
