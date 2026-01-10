package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.model.RestaurantUsers;
import ru.javawebinar.topjava.to.RestaurantTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.time.LocalDateTime.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    //public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "sumOfVotes");
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                   (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered").isEqualTo(e),
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered").isEqualTo(e));
    public static MatcherFactory.Matcher<RestaurantTo> TO_MATCHER =
            MatcherFactory.usingAssertions(RestaurantTo.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered").isEqualTo(e),
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered").isEqualTo(e));


    //public static MatcherFactory.Matcher<RestaurantTo> TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);

    public static final int RESTAURANT1_ID = START_SEQ + 4;
    public static final int RESTAURANT2_ID = START_SEQ + 5;
    public static final int RESTAURANT_NOT_FOUND = 10;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID,"ресторан0", "борщ 50, колтета с рисом 100, компот 10", LocalDate.now().atStartOfDay());
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "ресторан1", "суп куриный 40, сосиска с картофелем 80, чай 10", LocalDate.now().atStartOfDay());
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "ресторан1", "суп куриный 400, сосиска с картофелем 800, чай 100", LocalDateTime.of(2025, Month.DECEMBER, 25, 13, 50));
    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT1_ID + 3, "ресторан2", "суп харчо 600, плов 900, сок 100", LocalDateTime.of(2025, Month.DECEMBER, 25, 13, 50));
    public static final Restaurant restaurant5 = new Restaurant(RESTAURANT1_ID + 4, "ресторан2", "суп харчо 60, плов 90, сок 10", LocalDateTime.of(2025, Month.DECEMBER, 25, 13, 50));
    public static final Restaurant restaurant6 = new Restaurant(RESTAURANT1_ID + 5, "ресторан3", "суп сырный 40, лазанья 80, компот 10", LocalDate.now().atStartOfDay());
    public static final Restaurant restaurant7 = new Restaurant(RESTAURANT1_ID + 6, "ресторан2", "суп харчо 6, плов 9, сок 1", LocalDate.now().atStartOfDay());


    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant6, restaurant7, restaurant3, restaurant4, restaurant5);

    public static final RestaurantTo restaurantTo1 = getTo(restaurant1, 0);
    public static final RestaurantTo restaurantTo2 = getTo(restaurant2, 1);
    public static final RestaurantTo restaurantTo3 = getTo(restaurant3, 1);
    public static final RestaurantTo restaurantTo4 = getTo(restaurant4, 1);
    public static final RestaurantTo restaurantTo5 = getTo(restaurant5, 1);
    public static final RestaurantTo restaurantTo6 = getTo(restaurant6, 1);
    public static final RestaurantTo restaurantTo7 = getTo(restaurant7, 0);

    public static final RestaurantTo restaurantTo1AfterVote = getTo(restaurant1, 1);

    public static final List<RestaurantTo> restaurantTos = List.of(restaurantTo7,
            restaurantTo6, restaurantTo5, restaurantTo4, restaurantTo3, restaurantTo2, restaurantTo1);

    public static final List<RestaurantTo> restaurantTosForToday = List.of(restaurantTo7,
            restaurantTo6, restaurantTo2, restaurantTo1);




    public static List<RestaurantTo> getTosFromRest(List<Restaurant> restaurants) {
        List<RestaurantTo> restaurantTos = new ArrayList<>();
        int counter = 0;
        for (Restaurant currentRestaurant : restaurants) {
            for (RestaurantUsers restaurantUsers : RestaurantUsersTestData.restaurantUsersList) {
                if (restaurantUsers.getRestaurant().getId() == currentRestaurant.getId()) {
                    counter++;
                }
            }
            RestaurantTo restaurantTo = getTo(currentRestaurant, counter);
            restaurantTos.add(restaurantTo);
            counter = 0;
        }
        return restaurantTos;
    }
    public static Restaurant getNew() {
        return new Restaurant(null, "ресторан0", "суп харчо 0, плов 0, сок 0", LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0));
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID,"ресторан обновленный", "борщ обновленный, колтета с рисом обновленная, компот обновленный", LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0));
    }


    public static RestaurantTo getTo(Restaurant restaurant, int sumOfVotes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getMenu(), sumOfVotes, restaurant.getRegistered());
    }


}
