package com.github.denisdementevgithub.voteproject.web.restaurant;


import com.github.denisdementevgithub.voteproject.common.TimeUtil;
import com.github.denisdementevgithub.voteproject.user.service.RestaurantService;
import com.github.denisdementevgithub.voteproject.user.to.RestaurantTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.RestaurantUtils;
import com.github.denisdementevgithub.voteproject.user.web.restaurant.RestaurantUserRestController;
import com.github.denisdementevgithub.voteproject.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.ZoneId;

import static com.github.denisdementevgithub.voteproject.RestaurantTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RestaurantUserRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RestaurantUserRestController.REST_URL;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteSuccessful() throws Exception {
        restaurantService.setClock(TimeUtil.getClock(9, 0, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        TO_MATCHER.assertMatch(getRestaurantToAfterVoting(), restaurantTo1AfterVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteNotSuccessful() throws Exception {
        restaurantService.setClock(TimeUtil.getClock(11, 0, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    RestaurantTo getRestaurantToAfterVoting() {
        return restaurantService.getAll().stream()
                .filter(rest -> rest.getId() == RESTAURANT1_ID)
                .map(rest -> RestaurantUtils.restaurantToRestaurantTo(rest, restaurantService.getVotes().get(RESTAURANT1_ID)))
                .toList().getFirst();
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