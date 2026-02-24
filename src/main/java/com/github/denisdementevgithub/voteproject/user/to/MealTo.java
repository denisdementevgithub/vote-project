package com.github.denisdementevgithub.voteproject.user.to;

import com.github.denisdementevgithub.voteproject.common.model.AbstractBaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Getter
@Setter
public class MealTo extends AbstractBaseEntity {
    @NotBlank
    @Size(min = 2, max = 128)
    @Column(name = "name", nullable = false)
    @Pattern(regexp = "^[^<>]*$", message = "Name cannot contain HTML tags")
    protected String name;

    @Schema(description = "Price of meal")
    @Range(min = 1, max = 5000)
    public int price;

    @Schema(description = "Unique restaurant id")
    @Range(min = 100000)
    private int restaurant_id;

    @Schema(description = "Name of restaurant")
    //@NotBlank
    //@Size(min = 2, max = 128)
    private String restaurant_name;

    @Schema(description = "Sum of votes for a restaurant")
    private int sumOfVotes;

    @Schema(description = "Date of registered (also it is date of voting)")
    private LocalDateTime registered;

    //@ConstructorProperties({"id", "name", "price", "restaurant_id", "restaurant_name", "sumOfVotes", "registered"})
    public MealTo(Integer id, String name, int price, int restaurant_id, String restaurant_name, Integer sumOfVotes, LocalDateTime registered) {
        super(id);
        this.name = name;
        this.price = price;
        this.restaurant_id = restaurant_id;
        this.restaurant_name = restaurant_name;
        this.sumOfVotes = sumOfVotes;
        this.registered = registered;
    }

    @ConstructorProperties({"id", "name", "price", "restaurant_id"})
    public MealTo(Integer id, String name, int price, int restaurant_id) {
        super(id);
        this.name = name;
        this.price = price;
        this.restaurant_id = restaurant_id;
    }

}
