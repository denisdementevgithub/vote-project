package com.github.denisdementevgithub.voteproject.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import com.github.denisdementevgithub.voteproject.common.model.AbstractNamedEntity;

@Entity
@Getter
@Setter
@Schema(description = "Meal entity")
@Table(name = "restaurant_meal")
public class Meal extends AbstractNamedEntity {
    @Schema(description = "Price of meal")
    public int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Meal(int id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public Meal() {
    }

    public Meal(int id, String name, int price, Restaurant restaurant) {
        super(id, name);
        this.price = price;
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "price=" + price +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

}
