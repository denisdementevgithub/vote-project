package com.github.denisdementevgithub.voteproject;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;

import java.time.LocalDateTime;
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


    public static MealTo getNew() {
        return new MealTo(null, "новая еда", 10, 100004);
    }

    public static MealTo getUpdated() {
        MealTo mealTo = new MealTo(100011, "еда обновленный", 11, 100004);
        return mealTo;
    }
}
