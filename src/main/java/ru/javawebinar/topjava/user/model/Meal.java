package ru.javawebinar.topjava.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javawebinar.topjava.common.model.AbstractNamedEntity;

@Entity
@Getter
@Setter

@Table(name = "restaurant_meal")
public class Meal extends AbstractNamedEntity {
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
