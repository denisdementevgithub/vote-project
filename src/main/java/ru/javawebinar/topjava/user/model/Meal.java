package ru.javawebinar.topjava.user.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Meal {
    public String name;
    public int price;
}
