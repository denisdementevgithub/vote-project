package com.github.denisdementevgithub.voteproject.user.to;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.denisdementevgithub.voteproject.common.model.AbstractBaseEntity;
import com.github.denisdementevgithub.voteproject.common.to.BaseTo;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

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

    @Range(min = 100000)
    private int restaurant_id;

}
