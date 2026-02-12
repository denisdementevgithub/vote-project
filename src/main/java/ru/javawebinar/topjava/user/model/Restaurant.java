package ru.javawebinar.topjava.user.model;

import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;
import org.hibernate.type.SqlTypes;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.common.model.AbstractNamedEntity;
import ru.javawebinar.topjava.user.util.DateTimeUtil;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "restaurant")
@Getter
@Setter

public class Restaurant extends AbstractNamedEntity {
    //@Column(name = "menu", nullable = false)
    //@NotBlank
    //@Size(min = 2, max = 120)
    //@JdbcTypeCode(SqlTypes.JSON)
    //@Column(name = "menu", nullable = false, columnDefinition = "json")

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @BatchSize(size = 30)
    @OnDelete(action = OnDeleteAction.CASCADE)

    private List<Meal> menu;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime registered = LocalDateTime.now();


    public Restaurant() {
    }

    public Restaurant(Integer id, String name, List<Meal> menu, LocalDateTime registered) {
        super(id, name);
        this.menu = menu;
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "menu=" + menu +
                ", registered=" + registered +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
