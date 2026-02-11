package ru.javawebinar.topjava.web.restaurant;


import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.user.service.RestaurantService;
import ru.javawebinar.topjava.user.web.restaurant.RestaurantUserRestController;
import ru.javawebinar.topjava.web.AbstractControllerTest;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.RestaurantTestData.*;
import static ru.javawebinar.topjava.TestUtil.userHttpBasic;
import static ru.javawebinar.topjava.UserTestData.user;

class RestaurantUserRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantUserRestController.REST_URL;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void vote() throws Exception {
        try(MockedStatic<LocalTime> mocked = Mockito.mockStatic(LocalTime.class)) {
            perform(MockMvcRequestBuilders.post( REST_URL + "/" + RESTAURANT1_ID + "/vote")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isNoContent());
            TO_MATCHER.assertMatch(restaurantService.getAll().getLast(), restaurantTo1AfterVote);

        }
    }

    public static final String USER_MAIL = "user@yandex.ru";
    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(restaurantTosForToday));
    }

}