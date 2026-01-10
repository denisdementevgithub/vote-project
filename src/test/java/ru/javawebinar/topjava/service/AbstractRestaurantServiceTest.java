package ru.javawebinar.topjava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.Month;

import static java.time.LocalDateTime.of;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.javawebinar.topjava.RestaurantTestData.*;



public abstract class AbstractRestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    protected RestaurantService service;

    @Test
    void delete() {
        service.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(RESTAURANT_NOT_FOUND));
    }
/*
    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(RESTAURANT1_ID));
    }

 */

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.id();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    /*
    @Test
    void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, restaurant1.getRegistered(), "duplicate", 100), USER_ID));
    }

     */

    @Test
    void get() {
        Restaurant actual = service.get(RESTAURANT1_ID);
        RESTAURANT_MATCHER.assertMatch(actual, restaurant1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_NOT_FOUND));
    }
/*
    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

 */

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT1_ID), getUpdated());
    }
/*
    @Test
    void updateNotOwn() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(getUpdated()));
        Assertions.assertEquals("Not found entity with id=" + RESTAURANT1_ID, exception.getMessage());
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT1_ID), restaurant1);
    }


 */
    /*
    @Test
    void getAll() {
        RESTAURANT_MATCHER.assertMatch(service.getAll(), restaurants);
    }

     */

    /*
    @Test
    void getBetweenInclusive() {
        RESTAURANT_MATCHER.assertMatch(service.getBetweenInclusive(
                        LocalDate.of(2020, Month.JANUARY, 30),
                        LocalDate.of(2020, Month.JANUARY, 30), USER_ID),
                restaurant3, restaurant2, restaurant1);
    }

    @Test
    void getBetweenWithNullDates() {
        RESTAURANT_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), restaurants);
    }

     */
    @Test
    void createWithException() throws Exception {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "ресторан2", "", LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0))));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, null, "", LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0))));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "ресторан2", "", null)));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new Restaurant(null, "ресторан2", "", LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0))));
    }


}