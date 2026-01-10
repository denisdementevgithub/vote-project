package ru.javawebinar.topjava.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javawebinar.topjava.View;
import ru.javawebinar.topjava.util.DateTimeUtil;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Entity
@Table(name = "restaurant")
public class Restaurant extends AbstractNamedEntity {
    @Column(name = "menu", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)

    private String menu;

    @Column(name = "registered", nullable = false, columnDefinition = "timestamp default now()", updatable = false)
    @NotNull
    @DateTimeFormat(pattern = DateTimeUtil.DATE_TIME_PATTERN)
    private LocalDateTime registered = LocalDateTime.now();


    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String menu, LocalDateTime registered) {
        super(id, name);
        this.menu = menu;
        this.registered = registered;
    }


    public LocalDateTime getRegistered() {
        return registered;
    }

    public String getMenu() {
        return menu;
    }


    public LocalDate getDate() {
        return registered.toLocalDate();
    }

    public LocalTime getTime() {
        return registered.toLocalTime();
    }

    public void setRegistered(LocalDateTime registered) {
        this.registered = registered;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "menu='" + menu + '\'' +
                ", registered=" + registered +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
