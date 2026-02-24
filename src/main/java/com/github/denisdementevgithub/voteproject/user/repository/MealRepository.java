package com.github.denisdementevgithub.voteproject.user.repository;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
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

    @Query("SELECT m FROM Meal m order by m.id DESC")
    List<Meal> getAll();

    @Query("SELECT m FROM Meal m WHERE m.id = :id")
    Meal get(@Param("id") int id);

    /*
    неверно написано

    @Query(value = "SELECT m.id, CAST(COUNT(v.id) AS integer) as sumOfVotes FROM Meal m " +
            "LEFT JOIN Restaurant r ON r = m.restaurant " +
            "LEFT JOIN Vote v ON m = v.meal " +
            "WHERE r.registered between :startDateTime AND :endDateTime " +
            "GROUP BY r.id ORDER BY r.id ASC")
    //@Query("SELECT m FROM Meal m WHERE m.registered = :date")
    List<Object[]> getAllForTodayWithRestaurantAndVotes(@Param("startDateTime") LocalDateTime startDateTime,
                                                        @Param("endDateTime") LocalDateTime endDateTime);
*/
    @Query(value = "SELECT DISTINCT m FROM Meal m " +
            "LEFT JOIN FETCH m.restaurant " +
            "WHERE m.registered between :startDateTime AND :endDateTime " +
            "ORDER BY m.id ASC")
    List<Meal> getAllForToday(@Param("startDateTime") LocalDateTime startDateTime,
                                    @Param("endDateTime") LocalDateTime endDateTime);

    @Query(value = "SELECT DISTINCT m FROM Meal m " +
            "LEFT JOIN FETCH m.restaurant " +
            "WHERE (m.registered between :startDateTime AND :endDateTime) " +
            "AND m.restaurant.id = :restaurantId " +
            "ORDER BY m.id ASC")
    List<Meal> getMealsForRestaurantForToday(@Param("startDateTime") LocalDateTime startDateTime,
                              @Param("endDateTime") LocalDateTime endDateTime,
                                             @Param("restaurantId") int restaurantId);

    /*@Query(value = "SELECT r.id, CAST(COUNT(v.id) AS integer) as sumOfVotes FROM Restaurant r " +
            "LEFT JOIN Vote v ON r = v.restaurant " +
            "GROUP BY r.id ORDER BY r.id ASC")


    List<Object[]> getVotes();

    /*@Query(value = "SELECT m.restaurant.id, CAST(COUNT(v.id) AS integer) as sumOfVotes FROM Meal m " +
            "LEFT JOIN Vote v ON m = v.meal " +
            "WHERE m.registered between :startDateTime AND :endDateTime " +
            "GROUP BY m.id ORDER BY m.id ASC")


    List<Object[]> getVotesForDate(LocalDateTime startDateTime, LocalDateTime endDateTime);
*/
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id")
    int delete(@Param("id") int id);

    /*
    @Query(value = "SELECT DISTINCT v FROM Vote v " +
            "WHERE (v.localDate = :date) AND v.user.id = :userId " +
            "ORDER BY v.id ASC")
    List<Vote> getVotesForToday(@Param("userId")int userId, @Param("date") LocalDate date);

    @Modifying
    @Transactional
    @Query(value = "
        INSERT INTO vote (restaurant_id, user_id)
                ")
    int vote(int restaurantId, int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Modifying
    @Transactional
    /*@Query(value = "DELETE FROM Vote v WHERE v.user.id =?1 AND v.localDate =?2")


    int deleteVote(int userId, LocalDate date);
*/

    /*
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.restaurant=:restaurant")
    int deleteByRestaurant(@Param("restaurant") Restaurant restaurant);
*/

/*
    @Query("SELECT m FROM Meal m WHERE m.restaurant = :restaurant")
    List<Meal> getByRestaurant(@Param("restaurant") Restaurant restaurant);
   */
}
