package com.github.denisdementevgithub.voteproject.user.web.restaurant;

import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantUserRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "RestaurantUserRestController", description = "Use to interact with the Restaurant entities as an USER")
public class RestaurantUserRestController extends AbstractRestaurantController {
    public static final String REST_URL = "/api/profile/restaurants";

    @Override
    @GetMapping
    @Operation(summary = "Get all voting restaurants for today")
    public List<Restaurant> getAllForTodayWithMenu() {
        return super.getAllForTodayWithMenu();
    }

    @Override
    @PostMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Vote for an existing restaurant")
    public void vote(@PathVariable @Parameter(example = "100009") int id) {
        super.vote(id);
    }

    @Override
    @PutMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Revote for an existing restaurant")
    public void reVote(@PathVariable("id") @Parameter(example = "100009") int id) {
        super.reVote(id);
    }
}
