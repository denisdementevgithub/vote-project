package ru.javawebinar.topjava.to;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;
import java.util.Objects;

public class RestaurantTo extends BaseTo {

    private final String name;

    private final LocalDateTime registered;

    private final String menu;

    private final int sumOfVotes;

    @ConstructorProperties({"id", "name", "menu", "sumOfVotes", "registered"})
    public RestaurantTo(Integer id, String name, String menu, Integer sumOfVotes, LocalDateTime registered) {
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

    public String getMenu() {
        return menu;
    }

    public int getSumOfVotes() {
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
                "name='" + name + '\'' +
                ", registered=" + registered +
                ", menu='" + menu + '\'' +
                ", sumOfVotes=" + sumOfVotes +
                ", id=" + id +
                '}';
    }
}
