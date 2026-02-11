package ru.javawebinar.topjava.user.model;

import lombok.Getter;
import lombok.Setter;
import ru.javawebinar.topjava.common.model.AbstractBaseEntity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "restaurant_users")
@Getter
@Setter
public class RestaurantUsers extends AbstractBaseEntity {

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

}
