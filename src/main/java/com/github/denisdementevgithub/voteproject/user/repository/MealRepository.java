package com.github.denisdementevgithub.voteproject.user.repository;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.restaurant=:restaurant")
    int deleteByRestaurant(@Param("restaurant") Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int deleteById(@Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.restaurant = :restaurant")
    List<Meal> getByRestaurant(@Param("restaurant") Restaurant restaurant);

    @Query("SELECT m FROM Meal m WHERE m.id = :id")
    Meal getById(@Param("id") int id);

    @Query("SELECT m FROM Meal m order by m.id DESC")
    List<Meal> getAll();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM vote ru WHERE ru.user_id =?1 AND ru.voting_date =?2", nativeQuery = true)
    int deleteVote(int userId, LocalDate votingDate);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO vote (restaurant_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
    int vote(int id, int userId);
}
