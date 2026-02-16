package ru.javawebinar.topjava.user.web.restaurant;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.user.model.Meal;
import ru.javawebinar.topjava.user.model.Restaurant;
import ru.javawebinar.topjava.user.to.RestaurantTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantAdminRestController.REST_ADMIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantAdminRestController extends AbstractRestaurantController {
    public static final String REST_ADMIN_URL = "/api/admin/restaurants";

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/{id}/with-menu")
    public Restaurant getWithMenu(@PathVariable int id) {
        return super.getWithMenu(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }


    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        super.update(restaurant, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
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
    public void vote(@PathVariable int id) {
        super.vote(id);
    }

    @PostMapping("/{id}/menu")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setMenu(@PathVariable int id, @RequestBody List<Meal> menu) {
        super.setMenu(menu, id);
    }


}
