package com.github.denisdementevgithub.voteproject.user.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.to.RestaurantTo;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "RestaurantAdminRestController", description = "Use to interact with the Restaurant entities as an ADMIN")
public class RestaurantAdminRestController extends AbstractRestaurantController {
    public static final String REST_ADMIN_URL = "/api/admin/restaurants";

    @Override
    @GetMapping
    @Operation(summary = "Get all restaurants")
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get a restaurant without menu")
    public Restaurant get(@PathVariable @Parameter(example = "100009") int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/{id}/with-meals")
    @Operation(summary = "Get a restaurant with menu")
    public Restaurant getWithMenu(@PathVariable @Parameter(example = "100009") int id) {
        return super.getWithMenu(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a restaurant")
    public void delete(@PathVariable @Parameter(example = "100010") int id) {
        super.delete(id);
    }


    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update description of a restaurant")
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable @Parameter(example = "100009") int id) {
        super.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a new restaurant to the app")
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        Restaurant created = super.create(restaurant);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_ADMIN_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @PostMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Vote for an existing restaurant")
    public void vote(@PathVariable @Parameter(example = "100009") int id) {
        super.vote(id);
    }

    @PostMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Add menu to existing restaurant description")
    public void setMenu(@PathVariable @Parameter(example = "100009") int id, @RequestBody List<Meal> menu) {
        super.setMenu(menu, id);
    }
}
