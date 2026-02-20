package com.github.denisdementevgithub.voteproject.user.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "RestaurantUserRestController", description = "Use to interact with the Restaurant entities as an USER")
public class RestaurantUserRestController extends AbstractRestaurantController {
    public static final String REST_URL = "/api/profile/restaurants";

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get a restaurant without menu")
    public Restaurant get(@PathVariable @Parameter(example = "100009") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    @Operation(summary = "Get all voting restaurants for today")
    public List<RestaurantTo> getAllForToday() {
        return super.getAllForToday();
    }

    @Override
    @PostMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Vote for an existing restaurant")
    public void vote(@PathVariable @Parameter(example = "100009") int id) {
        super.vote(id);
    }
}
