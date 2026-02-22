package com.github.denisdementevgithub.voteproject.user.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Restaurant get(@Param("id") int id);

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    @EntityGraph(attributePaths = {"meals"}, type = EntityGraph.EntityGraphType.LOAD)
    Restaurant getWithMeals(@Param("id") int id);

    @Query(value = "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.meals ORDER BY r.id DESC")
    List<Restaurant> getAll();

    @Query(value = "SELECT r.id, CAST(COUNT(ru.id) AS integer) as sumOfVotes FROM Restaurant r " +
            "LEFT JOIN Vote ru ON r = ru.restaurant " +
            "GROUP BY r.id ORDER BY r.id DESC")
    List<Object[]> getVotes();


    @Query(value = "SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.meals " +
            "WHERE r.registered between :startDateTime AND :endDateTime " +
            "ORDER BY r.id DESC")
    List<Restaurant> getAllForToday(@Param("startDateTime") LocalDateTime startDateTime,
                                  @Param("endDateTime") LocalDateTime endDateTime);

}