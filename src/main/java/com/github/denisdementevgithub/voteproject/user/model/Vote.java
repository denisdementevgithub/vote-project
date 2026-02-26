package com.github.denisdementevgithub.voteproject.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import com.github.denisdementevgithub.voteproject.common.model.AbstractBaseEntity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "vote")
@Getter
@Setter
public class Vote extends AbstractBaseEntity {

    @JoinColumn(name = "restaurant_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "voting_date")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate localDate = LocalDate.now();

    public Vote() {
    }

    public Vote(Restaurant restaurant, User user) {
        this.restaurant = restaurant;
        this.user = user;
    }

    public Vote(Integer id, Restaurant restaurant, User user, LocalDate localDate) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.localDate = localDate;
    }

}
