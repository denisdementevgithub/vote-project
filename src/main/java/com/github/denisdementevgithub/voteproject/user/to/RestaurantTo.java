package com.github.denisdementevgithub.voteproject.user.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import com.github.denisdementevgithub.voteproject.common.to.BaseTo;
import com.github.denisdementevgithub.voteproject.user.model.Meal;


import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Schema(description = "Restaurant DTO (consists of Restaurant and sum of votes)")
public class RestaurantTo extends BaseTo {
    @Schema(description = "Entity's name")
    private String name;

    @Schema(description = "Date of registered")
    private LocalDateTime registered = LocalDateTime.now();

    @Schema(description = "Menu of the restaurant (consists of Meal)")
    private List<Meal> meals;

    @Schema(description = "Sum of votes for a restaurant")
    private int sumOfVotes;

    @ConstructorProperties({"id", "name", "menu", "sumOfVotes", "registered"})
    public RestaurantTo(Integer id, String name, List<Meal> meals, Integer sumOfVotes, LocalDateTime registered) {
        super(id);
        this.name = name;
        this.meals = meals;
        this.sumOfVotes = sumOfVotes;
        this.registered = registered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo restaurantTo = (RestaurantTo) o;
        return sumOfVotes == restaurantTo.sumOfVotes &&
                Objects.equals(id, restaurantTo.id) &&
                Objects.equals(registered, restaurantTo.registered) &&
                Objects.equals(meals, restaurantTo.meals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registered, meals, sumOfVotes);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registered=" + registered +
                ", meals=" + meals +
                ", sumOfVotes=" + sumOfVotes +
                '}';
    }
}
