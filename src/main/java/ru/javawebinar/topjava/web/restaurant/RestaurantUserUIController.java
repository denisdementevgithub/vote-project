package ru.javawebinar.topjava.web.restaurant;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.to.RestaurantTo;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ApiIgnore
@RestController
@RequestMapping(value = RestaurantUserUIController.REST_UI_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantUserUIController extends AbstractRestaurantController {
    static final String REST_UI_URL = "/users/restaurants";

    @Override
    @GetMapping
    public List<RestaurantTo> getAllForToday() {
        return super.getAllForToday();
    }

    @Override
    @PostMapping("/{id}/vote")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void vote(@PathVariable int id) {
        if ((super.get(id).getRegistered().toLocalDate()).equals(LocalDate.now()) &&
                (LocalTime.now().isBefore(LocalTime.of(11,00)))) {
            super.vote(id);
        } else {
            throw new IllegalRequestDataException("Голосование для данной даты закрыто");
        }
    }
}