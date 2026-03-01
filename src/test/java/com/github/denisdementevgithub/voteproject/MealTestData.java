package com.github.denisdementevgithub.voteproject;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.MealUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class MealTestData {
    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER =
            MatcherFactory.usingAssertions(Meal.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "restaurant").isEqualTo(e),
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "restaurant").isEqualTo(e));

    public static final MatcherFactory.Matcher<MealTo> TO_MATCHER =
            MatcherFactory.usingAssertions(MealTo.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "restaurant_id").isEqualTo(e),
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("registered", "restaurant_id").isEqualTo(e));

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

    public static MealTo getNew() {
        return new MealTo(null, "новая еда", 10, 100004);
    }

    public static MealTo getUpdated() {
        return new MealTo(100011, "еда обновленный", 11, 100004);
    }
}
