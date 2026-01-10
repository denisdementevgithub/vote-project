package ru.javawebinar.topjava.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "restaurant_users")
public class RestaurantUsers extends AbstractBaseEntity{

    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "voting_date")
    private LocalDate localDate;

    public RestaurantUsers() {
    }

    public RestaurantUsers(Integer id, Restaurant restaurant, User user, LocalDate localDate) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.localDate = localDate;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
