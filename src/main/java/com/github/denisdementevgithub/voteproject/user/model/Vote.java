package com.github.denisdementevgithub.voteproject.user.model;

import lombok.Getter;
import lombok.Setter;
import com.github.denisdementevgithub.voteproject.common.model.AbstractBaseEntity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "restaurant_user_vote")
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
    private LocalDate localDate;

    public Vote() {
    }

    public Vote(Integer id, Restaurant restaurant, User user, LocalDate localDate) {
        super(id);
        this.restaurant = restaurant;
        this.user = user;
        this.localDate = localDate;
    }

}
