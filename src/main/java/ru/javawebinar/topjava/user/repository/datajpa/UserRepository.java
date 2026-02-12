package ru.javawebinar.topjava.user.repository.datajpa;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.user.model.User;

import java.util.List;

import static ru.javawebinar.topjava.app.config.SecurityConfig.PASSWORD_ENCODER;


@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT u FROM User u WHERE u.id = :id")
    User get(@Param("id") int id);

    @Query("SELECT u FROM User u order by u.id ASC")
    List<User> getAll();

    @Query("SELECT u FROM User u WHERE LOWER(u.email) = LOWER(:email)")
    User getByEmail(String email);

    @Transactional
    default User prepareAndSave(User user) {
        user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
        user.setEmail(user.getEmail().toLowerCase());
        return save(user);
    }
/*
    default User getExistedByEmail(String email) {
        return getByEmail(email).orElseThrow(() -> new NotFoundException("User with email=" + email + " not found"));
    }

 */
}
