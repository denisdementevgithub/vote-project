package com.github.denisdementevgithub.voteproject.user.web.meal;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.service.MealService;
import com.github.denisdementevgithub.voteproject.user.service.RestaurantService;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;
import com.github.denisdementevgithub.voteproject.user.to.RestaurantTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.MealUtils;
import com.github.denisdementevgithub.voteproject.user.web.restaurant.RestaurantAdminRestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.github.denisdementevgithub.voteproject.common.validation.ValidationUtil.assureIdConsistent;

@RestController
@RequestMapping(value = MealAdminRestController.MEAL_ADMIN_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "MealAdminRestController", description = "Use to interact with the Meal entities as an ADMIN")
public class MealAdminRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    public static final String MEAL_ADMIN_REST_URL = "/api/admin/meals";

    @Autowired
    private MealService service;

    @GetMapping
    @Operation(summary = "Get all meals")
    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a meal by id")
    public Meal getById(@PathVariable @Parameter(example = "100034") int id) {
        log.info("get meal by id {}", id);
        return service.getById(id);
    }

    @GetMapping("/searchByRestaurantId/{id}")
    @Operation(summary = "Get a meal by id")
    public List<Meal> getByRestaurant(@PathVariable @Parameter(example = "100009") int id) {
        log.info("get meal by restaurant with id {}", id);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(id);
        return service.getByRestaurant(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a meal")
    public void delete(@PathVariable @Parameter(example = "100034") int id) {
        service.deleteById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update description of a meal")
    public void update(@Valid @RequestBody MealTo mealTo, @PathVariable @Parameter(example = "100034") int id) {
        log.info("update {}", mealTo);
        Meal meal = MealUtils.MealToToMeal(mealTo);
        assureIdConsistent(meal, id);
        service.update(meal);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a new meal to the app")
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody MealTo mealTo) {
        log.info("createWithLocation {}", mealTo);
        Meal meal = MealUtils.MealToToMeal(mealTo);
        Meal created = service.create(meal);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(MEAL_ADMIN_REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }





}
