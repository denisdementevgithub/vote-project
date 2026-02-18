package ru.javawebinar.topjava.user.repository.datajpa;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.common.error.NotFoundException;
import ru.javawebinar.topjava.user.model.Restaurant;

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
    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    Restaurant getWithMenu(@Param("id") int id);

    @Query(value = "SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu ORDER BY r.id DESC")
    List<Restaurant> getAll();

    @Query(value = "SELECT r.id, CAST(COUNT(ru.id) AS integer) as sumOfVotes FROM Restaurant r " +
            "LEFT JOIN Vote ru ON r = ru.restaurant " +
            "GROUP BY r.id ORDER BY r.id DESC")
    List<Object[]> getVotes();


    @Query(value = "SELECT DISTINCT r FROM Restaurant r " +
            "LEFT JOIN FETCH r.menu " +
            "WHERE r.registered between :startDateTime AND :endDateTime " +
            "ORDER BY r.id DESC")
    List<Restaurant> getAllForToday(@Param("startDateTime") LocalDateTime startDateTime,
                                  @Param("endDateTime") LocalDateTime endDateTime);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO restaurant_user_vote (restaurant_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
    int vote(int id, int userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM restaurant_user_vote ru WHERE ru.user_id =?1 AND ru.voting_date =?2", nativeQuery = true)
    int deleteVote(int userId, LocalDate votingDate);

    default Restaurant getExisted(int id) {
        return findById(id).orElseThrow(() -> new NotFoundException("Entity with id=" + id + " not found"));
    }
}