package com.github.denisdementevgithub.voteproject.user.repository;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import com.github.denisdementevgithub.voteproject.user.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Query(value = "SELECT m FROM Meal m " +
            "LEFT JOIN FETCH m.restaurant " +
            "ORDER BY m.id ASC")
    List<Meal> getAll();

    @Query(value = "SELECT m FROM Meal m " +
            "LEFT JOIN FETCH m.restaurant " +
            "WHERE m.registered between :startDateTime AND :endDateTime " +
            "ORDER BY m.id ASC")
    List<Meal> getAllForToday(@Param("startDateTime") LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime);

    @Query("SELECT m FROM Meal m WHERE m.id = :id")
    Meal get(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);
}
