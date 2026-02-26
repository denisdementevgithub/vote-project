package com.github.denisdementevgithub.voteproject.web.meal;


import com.github.denisdementevgithub.voteproject.MealTestData;
import com.github.denisdementevgithub.voteproject.common.error.NotFoundException;
import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.service.MealService;
import com.github.denisdementevgithub.voteproject.user.to.MealTo;
import com.github.denisdementevgithub.voteproject.user.web.converter.MealUtils;
import com.github.denisdementevgithub.voteproject.user.web.json.JsonUtil;
import com.github.denisdementevgithub.voteproject.user.web.meal.MealAdminRestController;
import com.github.denisdementevgithub.voteproject.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static com.github.denisdementevgithub.voteproject.MealTestData.MEAL_MATCHER;
import static com.github.denisdementevgithub.voteproject.MealTestData.TO_MATCHER;
import static com.github.denisdementevgithub.voteproject.RestaurantTestData.*;
import static com.github.denisdementevgithub.voteproject.common.error.ErrorType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MealAdminRestControllerTest extends AbstractControllerTest {
    public static final String REST_ADMIN_URL = MealAdminRestController.MEAL_ADMIN_REST_URL;
    public static final String ADMIN_MAIL = "admin@gmail.com";

    @Autowired
    private MealService mealService;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/all"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(mealTos));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllForToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(TO_MATCHER.contentJson(mealTosForToday));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/" + 100011))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(meal100011));
    }

    @Test
    void getUnauth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/" + meal100011.id()))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_ADMIN_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + "/" + meal100011.id()))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(meal100011.id()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_ADMIN_URL + "/" + NOT_FOUND))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        MealTo update = MealTestData.getUpdated();
        perform(MockMvcRequestBuilders.put(REST_ADMIN_URL + "/" + meal100011.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(update)))
                .andExpect(status().isNoContent());
        Meal updateMeal = MealUtils.MealToToMeal(update);
        MEAL_MATCHER.assertMatch(mealService.get(meal100011.id()), updateMeal);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        MealTo newMealTo = MealTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMealTo)))
                .andExpect(status().isCreated());
        Meal created = MEAL_MATCHER.readFromJson(action);
        int newId = created.id();
        newMealTo.setId(newId);
        Meal newMeal = MealUtils.MealToToMeal(newMealTo);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealService.get(newId), newMeal);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Meal invalid = new Meal(null, null, 0, LocalDateTime.now());
        perform(MockMvcRequestBuilders.post(REST_ADMIN_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Meal invalid = new Meal(meal100011.id(), null, 0, LocalDateTime.now());
        perform(MockMvcRequestBuilders.put(REST_ADMIN_URL + "/" + meal100011.id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR));
    }
}