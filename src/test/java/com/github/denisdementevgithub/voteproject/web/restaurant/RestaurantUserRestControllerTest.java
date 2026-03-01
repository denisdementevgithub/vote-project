package com.github.denisdementevgithub.voteproject.web.restaurant;


import com.github.denisdementevgithub.voteproject.VoteTestData;
import com.github.denisdementevgithub.voteproject.common.TimeUtil;
import com.github.denisdementevgithub.voteproject.user.service.RestaurantService;
import com.github.denisdementevgithub.voteproject.user.service.VoteService;
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
    static final String REST_URL = RestaurantUserRestController.REST_URL;
    public static final String USER_MAIL = "user@yandex.ru";

    @Autowired
    private VoteService voteService;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllForTodayWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurantsForToday));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteSuccessful() throws Exception {
        voteService.setClock(TimeUtil.getClock(9, 0, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.get("/api/votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(VoteTestData.afterVoting));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void voteNotSuccessful() throws Exception {
        voteService.setClock(TimeUtil.getClock(15, 0, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void reVoteSuccessful() throws Exception {
        voteService.setClock(TimeUtil.getClock(10, 0, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + (RESTAURANT1_ID + 6) + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.get("/api/votes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(VoteTestData.afterReVoting));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void reVoteNotSuccessful() throws Exception {
        voteService.setClock(TimeUtil.getClock(15, 0, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_URL + "/" + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        perform(MockMvcRequestBuilders.put(REST_URL + "/" + (RESTAURANT1_ID + 6) + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}