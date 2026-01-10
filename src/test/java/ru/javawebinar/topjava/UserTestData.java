package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.RestaurantTestData.*;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {

    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");


    public static final int USER_ID0 = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int USER_ID1 = START_SEQ + 2;
    public static final int USER_ID2 = START_SEQ + 3;;
    public static final int USER_NOT_FOUND = 10;


    public static final User user = new User(USER_ID0, "User Anton", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin Andrey", "admin@gmail.com", "admin", Role.ADMIN, Role.USER);
    public static final User user1 = new User(USER_ID1, "Guest Sergey", "guestSergey@gmail.com", "password", Role.USER);
    public static final User user2 = new User(USER_ID2, "Guest Sveta", "guestSveta@gmail.com", "password", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);


        updated.setName("UpdatedName");

        updated.setPassword("newPass");
        updated.setEnabled(false);
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }


}
