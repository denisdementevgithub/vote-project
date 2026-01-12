package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudRestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query(value = "SELECT r.id, r.name, r.menu, COUNT (ru.id), r.registered FROM Restaurant r " +
            "LEFT JOIN RestaurantUsers ru ON r = ru.restaurant " +
            "GROUP BY r.id, r.name, r.menu ORDER BY r.id DESC")
    List<Object[]> getAll();

    @Query(value = "SELECT r.id, r.name, r.menu, COUNT (ru.id), r.registered FROM Restaurant r " +
            "LEFT JOIN RestaurantUsers ru ON r = ru.restaurant WHERE r.registered between :startDateTime AND :endDateTime " +
            "GROUP BY r.id, r.name, r.menu ORDER BY r.id DESC")
    List<Object[]> getAllForToday(@Param("startDateTime") LocalDateTime startDateTime,
                                  @Param("endDateTime") LocalDateTime endDateTime);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO restaurant_users (restaurant_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
    int vote(int id, int userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM restaurant_users ru WHERE ru.user_id =?1 AND ru.voting_date =?2", nativeQuery = true)
    int deleteVote(int userId, LocalDate votingDate);
}