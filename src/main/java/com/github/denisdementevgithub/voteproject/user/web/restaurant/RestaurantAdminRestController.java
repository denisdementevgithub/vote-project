package com.github.denisdementevgithub.voteproject.user.web.restaurant;

import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "RestaurantAdminRestController", description = "Use to interact with the Restaurant entities as an ADMIN")
public class RestaurantAdminRestController extends AbstractRestaurantController {
    public static final String REST_ADMIN_URL = "/api/admin/restaurants";

    @Override
    @GetMapping("/all")
    @Operation(summary = "Get all restaurants")
    public List<Restaurant> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping
    @Operation(summary = "Get all restaurants for today")
    public List<Restaurant> getAllForTodayWithMenu() {
        return super.getAllForTodayWithMenu();
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get a restaurant without menu")
    public Restaurant get(@PathVariable @Parameter(example = "100009") int id) {
        return super.get(id);
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
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update description of a restaurant")
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable @Parameter(example = "100009") int id) {
        super.update(restaurant, id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a restaurant")
    public void delete(@PathVariable @Parameter(example = "100010") int id) {
        super.delete(id);
    }
}
