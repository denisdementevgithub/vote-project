package com.github.denisdementevgithub.voteproject;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.denisdementevgithub.voteproject.MealTestData.*;
import static com.github.denisdementevgithub.voteproject.common.model.AbstractBaseEntity.START_SEQ;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RestaurantTestData {

    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "meals").isEqualTo(e),
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "meals").isEqualTo(e));

    public static final int RESTAURANT1_ID = START_SEQ + 4;
    public static final int RESTAURANT2_ID = START_SEQ + 5;
    public static final int NOT_FOUND = 10;


    public static final Restaurant restaurant100004 = new Restaurant(RESTAURANT1_ID, "Столовая", List.of(meal100011, meal100012, meal100013));
    public static final Restaurant restaurant100005 = new Restaurant(RESTAURANT2_ID, "Семейный ресторан", List.of(meal100014, meal100015, meal100016));
    public static final Restaurant restaurant100006 = new Restaurant(RESTAURANT1_ID + 2, "Сказка Востока", List.of(meal100017, meal100018, meal100019));
    public static final Restaurant restaurant100007 = new Restaurant(RESTAURANT1_ID + 3, "Almond", List.of(meal100020, meal100021, meal100022));
    public static final Restaurant restaurant100008 = new Restaurant(RESTAURANT1_ID + 4, "Цзао Ван", List.of(meal100023, meal100024, meal100025));
    public static final Restaurant restaurant100009 = new Restaurant(RESTAURANT1_ID + 5, "Прованс", List.of(meal100026, meal100027, meal100028));
    public static final Restaurant restaurant100010 = new Restaurant(RESTAURANT1_ID + 6, "Ферма Бенуа", List.of(meal100029, meal100030, meal100031));

    public static final List<Restaurant> restaurants = List.of(restaurant100004, restaurant100005, restaurant100006, restaurant100007, restaurant100008, restaurant100009, restaurant100010);
    public static final List<Restaurant> restaurantsForToday = List.of(restaurant100004, restaurant100005, restaurant100006, restaurant100007, restaurant100010);

    public static Restaurant getNew() {
        return new Restaurant(null, "ресторан0", List.of(new Meal(100037, "суп харчо", 10, LocalDateTime.now()), new Meal(100038, "плов", 10, LocalDateTime.now()), new Meal(100039, "сок", 10, LocalDateTime.now())));
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "ресторан обновленный", List.of(new Meal(100016, "борщ", 50, LocalDateTime.now()), new Meal(100017, "колтета с рисом", 100, LocalDateTime.now()), new Meal(100018, "компот", 10, LocalDateTime.now())));
    }
}
