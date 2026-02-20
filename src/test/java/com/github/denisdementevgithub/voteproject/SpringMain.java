package com.github.denisdementevgithub.voteproject;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.github.denisdementevgithub.voteproject.user.model.Role;
import com.github.denisdementevgithub.voteproject.user.model.User;
import com.github.denisdementevgithub.voteproject.user.web.user.AdminRestController;

import java.util.Arrays;

import static com.github.denisdementevgithub.voteproject.TestUtil.mockAuthorize;
import static com.github.denisdementevgithub.voteproject.UserTestData.user;


public class SpringMain {
    public static void main(String[] args) {

        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/inmemory.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            System.out.println();

            mockAuthorize(user);


            System.out.println();
        }


    }

}
