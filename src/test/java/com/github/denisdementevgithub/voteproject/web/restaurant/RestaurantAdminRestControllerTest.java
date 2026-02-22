package com.github.denisdementevgithub.voteproject.web.restaurant;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.github.denisdementevgithub.voteproject.RestaurantTestData;
import com.github.denisdementevgithub.voteproject.common.TimeUtil;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.service.RestaurantService;
import com.github.denisdementevgithub.voteproject.common.error.NotFoundException;
import com.github.denisdementevgithub.voteproject.user.to.RestaurantTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.RestaurantUtils;
import com.github.denisdementevgithub.voteproject.user.web.restaurant.RestaurantAdminRestController;
import com.github.denisdementevgithub.voteproject.web.AbstractControllerTest;
import com.github.denisdementevgithub.voteproject.user.web.json.JsonUtil;

import java.time.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.denisdementevgithub.voteproject.RestaurantTestData.*;
import static com.github.denisdementevgithub.voteproject.TestUtil.userHttpBasic;
import static com.github.denisdementevgithub.voteproject.UserTestData.*;

import static com.github.denisdementevgithub.voteproject.common.error.ErrorType.VALIDATION_ERROR;

class RestaurantAdminRestControllerTest extends AbstractControllerTest {
    public static final String REST_ADMIN_URL = RestaurantAdminRestController.REST_ADMIN_URL;

    @Autowired
    private RestaurantService restaurantService;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/" + (RESTAURANT1_ID + 1)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant2));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/" + RESTAURANT1_ID))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/" + RESTAURANT_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + "/" + RESTAURANT1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> restaurantService.get(RESTAURANT1_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + "/" + RESTAURANT_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = RestaurantTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_ADMIN_URL + "/" + RESTAURANT1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(RESTAURANT1_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());
        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantService.get(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void voteSuccessful() throws Exception {
        restaurantService.setClock(TimeUtil.getClock(9, 0, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_ADMIN_URL + "/" + RESTAURANT1_ID + "/vote")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
        TO_MATCHER.assertMatch(getRestaurantToAfterVoting(), restaurantTo1AfterVote);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void voteNotSuccessful() throws Exception {
        restaurantService.setClock(TimeUtil.getClock(15, 0, ZoneId.systemDefault()));
        perform(MockMvcRequestBuilders.post(REST_ADMIN_URL + "/" + RESTAURANT1_ID + "/vote")
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

    public static final String ADMIN_MAIL = "admin@gmail.com";

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(restaurantTos));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant invalid = new Restaurant(null, null, null, LocalDateTime.now());
        perform(MockMvcRequestBuilders.post(REST_ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant invalid = new Restaurant(RESTAURANT1_ID, null, null, LocalDateTime.now());
        perform(MockMvcRequestBuilders.put(REST_ADMIN_URL + "/" + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid))
                .with(userHttpBasic(admin)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }
}