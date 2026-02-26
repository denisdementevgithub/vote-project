package com.github.denisdementevgithub.voteproject;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.MealUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "meals").isEqualTo(e),
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "meals").isEqualTo(e));
    /*
    public static final MatcherFactory.Matcher<RestaurantTo> TO_MATCHER =
            MatcherFactory.usingAssertions(RestaurantTo.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "meals").isEqualTo(e),
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "meals").isEqualTo(e));


     */
    public static final int RESTAURANT1_ID = START_SEQ + 4;
    public static final int RESTAURANT2_ID = START_SEQ + 5;
    public static final int NOT_FOUND = 10;

    public static final Meal meal100011 = new Meal(100011, "борщ", 50, LocalDateTime.now());
    public static final Meal meal100012 = new Meal(100012, "колтета с рисом", 100, LocalDateTime.now());
    public static final Meal meal100013 = new Meal(100013, "компот", 10, LocalDateTime.now());
    public static final Meal meal100014 = new Meal(100014, "суп куриный", 40, LocalDateTime.now());
    public static final Meal meal100015 = new Meal(100015, "сосиска с картофелем", 80, LocalDateTime.now());
    public static final Meal meal100016 = new Meal(100016, "чай", 10, LocalDateTime.now());
    public static final Meal meal100017 = new Meal(100017, "суп куриный", 400, LocalDateTime.now());
    public static final Meal meal100018 = new Meal(100018, "сосиска с картофелем", 800, LocalDateTime.now());
    public static final Meal meal100019 = new Meal(100019, "чай", 100, LocalDateTime.now());
    public static final Meal meal100020 = new Meal(100020, "суп харчо", 600, LocalDateTime.now());
    public static final Meal meal100021 = new Meal(100021, "плов", 900, LocalDateTime.now());
    public static final Meal meal100022 = new Meal(100022, "сок", 100, LocalDateTime.now());
    public static final Meal meal100023 = new Meal(100023, "суп харчо", 60, LocalDateTime.now());
    public static final Meal meal100024 = new Meal(100024, "плов", 90, LocalDateTime.now());
    public static final Meal meal100025 = new Meal(100025, "сок", 10, LocalDateTime.now());
    public static final Meal meal100026 = new Meal(100026, "суп сырный", 40, LocalDateTime.now());
    public static final Meal meal100027 = new Meal(100027, "лазанья", 80, LocalDateTime.now());
    public static final Meal meal100028 = new Meal(100028, "компот", 10, LocalDateTime.now());
    public static final Meal meal100029 = new Meal(100029, "суп харчо", 6, LocalDateTime.now());
    public static final Meal meal100030 = new Meal(100030, "плов", 9, LocalDateTime.now());
    public static final Meal meal100031 = new Meal(100031, "сок", 1, LocalDateTime.now());

    public static final List<Meal> meals = List.of(meal100011, meal100012, meal100013, meal100014, meal100015, meal100016,
            meal100017, meal100018, meal100019, meal100020, meal100021, meal100022, meal100023, meal100024, meal100025,
            meal100026, meal100027, meal100028, meal100029, meal100030, meal100031);

    public static final List<MealTo> mealTos = listOfTestMealsToListOfTestMealTos(meals);

    public static final List<MealTo> mealTosForToday = listOfTestMealsToListOfTestMealTos(List.of(meal100011, meal100012, meal100013,
            meal100014, meal100015, meal100016, meal100017, meal100018, meal100019, meal100023, meal100024, meal100025,
            meal100029, meal100030, meal100031));

    public static List<MealTo> listOfTestMealsToListOfTestMealTos(List<Meal> meals) {
        List<MealTo> listOfMealTo = new ArrayList<>();
        for (Meal meal:meals) {
            Restaurant restaurant = new Restaurant();
            restaurant.setId(10);
            meal.setRestaurant(restaurant);
            listOfMealTo.add(MealUtils.mealToMealTo(meal));
        }
        return listOfMealTo;
    }


    public static final Restaurant restaurant100004 = new Restaurant(RESTAURANT1_ID, "Столовая", List.of(meal100011, meal100012, meal100013));
    public static final Restaurant restaurant100005 = new Restaurant(RESTAURANT2_ID, "Семейный ресторан", List.of(meal100014, meal100015, meal100016));
    public static final Restaurant restaurant100006 = new Restaurant(RESTAURANT1_ID + 2, "Сказка Востока", List.of(meal100017, meal100018, meal100019));
    public static final Restaurant restaurant100007 = new Restaurant(RESTAURANT1_ID + 3, "Almond", List.of(meal100020, meal100021, meal100022));
    public static final Restaurant restaurant100008 = new Restaurant(RESTAURANT1_ID + 4, "Цзао Ван", List.of(meal100023, meal100024, meal100025));
    public static final Restaurant restaurant100009 = new Restaurant(RESTAURANT1_ID + 5, "Прованс", List.of(meal100026, meal100027, meal100028));
    public static final Restaurant restaurant100010 = new Restaurant(RESTAURANT1_ID + 6, "Ферма Бенуа", List.of(meal100029, meal100030, meal100031));

    public static final List<Restaurant> restaurants = List.of(restaurant100004, restaurant100005, restaurant100006, restaurant100007, restaurant100008, restaurant100009, restaurant100010);
    public static final List<Restaurant> restaurantsForToday = List.of(restaurant100004, restaurant100005, restaurant100006, restaurant100007, restaurant100010);

    /*
        public static final RestaurantTo restaurantTo100004 = getTo(restaurant100004, 1);
        public static final RestaurantTo restaurantTo100005 = getTo(restaurant100005, 0);
        public static final RestaurantTo restaurantTo100006 = getTo(restaurant100006, 0);
        public static final RestaurantTo restaurantTo100007 = getTo(restaurant100007, 1);
        public static final RestaurantTo restaurantTo100008 = getTo(restaurant100008, 0);
        public static final RestaurantTo restaurantTo100009 = getTo(restaurant100009, 0);
        public static final RestaurantTo restaurantTo100010 = getTo(restaurant100010, 1);

        public static final RestaurantTo restaurantTo100007AfterVote = getTo(restaurant100007, 1);

        public static final List<RestaurantTo> restaurantTosForAll = List.of(restaurantTo100004,
                restaurantTo100005, restaurantTo100006, restaurantTo100007, restaurantTo100008, restaurantTo100009, restaurantTo100010);

        public static final List<RestaurantTo> restaurantTosForToday = List.of(restaurantTo100010,
                restaurantTo100007, restaurantTo100006, restaurantTo100005, restaurantTo100004);


     */
    public static Restaurant getNew() {
        return new Restaurant(null, "ресторан0", List.of(new Meal(100037, "суп харчо", 10, LocalDateTime.now()), new Meal(100038, "плов", 10, LocalDateTime.now()), new Meal(100039, "сок", 10, LocalDateTime.now())));
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "ресторан обновленный", List.of(new Meal(100016, "борщ", 50, LocalDateTime.now()), new Meal(100017, "колтета с рисом", 100, LocalDateTime.now()), new Meal(100018, "компот", 10, LocalDateTime.now())));
    }

    /*
    public static RestaurantTo getTo(Restaurant restaurant, int sumOfVotes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), restaurant.getMeals(), sumOfVotes, restaurant.getRegistered());
    }

     */
}
