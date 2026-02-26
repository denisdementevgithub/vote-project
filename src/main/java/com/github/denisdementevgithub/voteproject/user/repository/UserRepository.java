package com.github.denisdementevgithub.voteproject.user.repository;

import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.github.denisdementevgithub.voteproject.user.model.User;

import java.util.List;

import static com.github.denisdementevgithub.voteproject.app.config.SecurityConfig.PASSWORD_ENCODER;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u FROM User u order by u.id ASC")
    List<User> getAll();

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User get(@Param("id") int id);

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    User getByEmail(String email);

    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);
}
