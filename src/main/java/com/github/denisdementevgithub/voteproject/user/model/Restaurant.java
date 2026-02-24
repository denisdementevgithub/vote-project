package com.github.denisdementevgithub.voteproject.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.github.denisdementevgithub.voteproject.common.model.AbstractNamedEntity;
import com.github.denisdementevgithub.voteproject.user.util.DateTimeUtil;


import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "restaurant")
@Getter
@Setter
@Schema(description = "Restaurant entity")
public class Restaurant extends AbstractNamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @BatchSize(size = 30)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Schema(description = "Menu of the restaurant (consists of Meal)")
    private List<Meal> meals;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    @Schema(description = "Date of registered")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime registered = LocalDateTime.now();

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, List<Meal> meals) {
        super(id, name);
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", meals=" + meals +
                ", registered=" + registered +
                '}';
    }
}
