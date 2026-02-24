package com.github.denisdementevgithub.voteproject.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.denisdementevgithub.voteproject.user.util.DateTimeUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.github.denisdementevgithub.voteproject.common.model.AbstractNamedEntity;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Schema(description = "Meal entity")
@Table(name = "meal")
public class Meal extends AbstractNamedEntity {
    @Schema(description = "Price of meal")
    @Range(min = 1, max = 5000)
    public int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    @Schema(description = "Date of registered (also it is date of voting)")
    private LocalDateTime registered = LocalDateTime.now();

    public Meal(int id, String name, int price, LocalDateTime registered) {
        super(id, name);
        this.price = price;
        this.registered = registered;
    }

    public Meal() {
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", restaurant=" + restaurant +
                ", registered=" + registered +
                '}';
    }
}
