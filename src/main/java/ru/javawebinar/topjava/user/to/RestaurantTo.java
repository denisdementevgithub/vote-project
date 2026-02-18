package ru.javawebinar.topjava.user.to;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;
import ru.javawebinar.topjava.common.to.BaseTo;
import ru.javawebinar.topjava.user.model.Meal;


import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Schema(description = "Restaurant DTO (consists of Restaurant and sum of votes)")
public class RestaurantTo extends BaseTo {
    @Schema(description = "Entity's name")
    private String name;

    @Schema(description = "Date of registered")
    private LocalDateTime registered;

    @Schema(description = "Menu of the restaurant (consists of Meal)")
    private List<Meal> menu = new ArrayList<>();

    @Schema(description = "Sum of votes for a restaurant")
    private int sumOfVotes;

    @ConstructorProperties({"id", "name", "menu", "sumOfVotes", "registered"})
    public RestaurantTo(Integer id, String name, List<Meal> menu, Integer sumOfVotes, LocalDateTime registered) {
        super(id);
        this.name = name;
        this.menu = menu;
        this.sumOfVotes = sumOfVotes;
        this.registered = registered;
    }






    public String getName() {
        return name;
    }

    public LocalDateTime getRegistered() {
        return registered;
    }

    public List<Meal> getMenu() {
        return menu;
    }

    public long getSumOfVotes() {
        return sumOfVotes;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantTo restaurantTo = (RestaurantTo) o;
        return sumOfVotes == restaurantTo.sumOfVotes &&
                Objects.equals(id, restaurantTo.id) &&
                Objects.equals(registered, restaurantTo.registered) &&
                Objects.equals(menu, restaurantTo.menu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registered, menu, sumOfVotes);
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", registered=" + registered +
                ", menu=" + menu +
                ", sumOfVotes=" + sumOfVotes +
                '}';
    }
}
