package ru.javawebinar.topjava.web.restaurant;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.RestaurantTestData;
import ru.javawebinar.topjava.model.Restaurant;
import ru.javawebinar.topjava.service.RestaurantService;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.RestaurantTestData.*;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.admin;
import static ru.javawebinar.topjava.UserTestData.user;
import static ru.javawebinar.topjava.util.exception.ErrorType.VALIDATION_ERROR;

class RestaurantUserRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantUserRestController.REST_URL + '/';

    @Autowired
    private RestaurantService restaurantService;

    @Test
    void vote() throws Exception {
        perform(MockMvcRequestBuilders.post( REST_URL + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .with(userHttpBasic(user)))
                .andDo(print())
                .andExpect(status().isNoContent());
        TO_MATCHER.assertMatch(restaurantService.getAll().getLast(), restaurantTo1AfterVote);
    }


    @Test
    void getAllForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(user)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(restaurantTosForToday));
    }

}