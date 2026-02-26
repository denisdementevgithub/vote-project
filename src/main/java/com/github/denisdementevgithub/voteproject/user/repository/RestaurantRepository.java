package com.github.denisdementevgithub.voteproject.user.repository;

import com.github.denisdementevgithub.voteproject.user.model.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Query("SELECT r FROM Restaurant r ORDER BY r.id ASC")
    List<Restaurant> getAll();

    @Query("SELECT r FROM Restaurant r WHERE r.id = :id")
    Restaurant get(@Param("id") int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);
}