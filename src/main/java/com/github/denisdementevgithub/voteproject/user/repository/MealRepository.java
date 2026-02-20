package com.github.denisdementevgithub.voteproject.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.restaurant=:restaurant")
    int delete(@Param("restaurant") Restaurant restaurant);

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    @Query("SELECT m FROM Meal m WHERE m.restaurant = :restaurant")
    List<Meal> getByRestaurant(@Param("restaurant") Restaurant restaurant);

    @Query("SELECT m FROM Meal m WHERE m.id = :id")
    Meal get(@Param("id") int id);

    @Query("SELECT m FROM Meal m order by m.id DESC")
    List<Meal> getAll();

}
