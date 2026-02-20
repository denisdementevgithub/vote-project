package com.github.denisdementevgithub.voteproject.user.web.menu;

import org.springframework.web.bind.annotation.RestController;
import com.github.denisdementevgithub.voteproject.user.repository.datajpa.RestaurantRepository;

@RestController
public class MealAdminRestController extends AbstractMealController {
    public static final String REST_ADMIN_URL = "/api/admin/meals";

    public MealAdminRestController(RestaurantRepository crudRestaurantRepository) {
        super();
    }

}
