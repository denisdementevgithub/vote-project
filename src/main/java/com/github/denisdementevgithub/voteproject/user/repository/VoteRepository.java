package com.github.denisdementevgithub.voteproject.user.repository;

import com.github.denisdementevgithub.voteproject.user.model.Meal;
import com.github.denisdementevgithub.voteproject.user.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    @Query(value = """
            SELECT v.restaurant_id, CAST(COUNT(v.id) AS integer) as sumOfVotes FROM Vote v
            GROUP BY v.restaurant_id ORDER BY v.id ASC
                        """, nativeQuery = true)
    List<Object[]> getAll();

    @Query(value = """
            SELECT v.restaurant_id, CAST(COUNT(v.id) AS integer) as sumOfVotes FROM Vote v
            WHERE v.voting_date = :date  
            GROUP BY v.restaurant_id ORDER BY v.restaurant_id ASC
                        """, nativeQuery = true)
    List<Object[]> getAllForDate(LocalDate date);

    @Query(value = "SELECT v FROM Vote v " +
            "WHERE (v.localDate = :date) AND v.user.id = :userId " +
            "ORDER BY v.id ASC")
    List<Vote> getVotesForTodayForUser(@Param("userId")int userId, @Param("date") LocalDate date);

    @Modifying
    @Query(value = "INSERT INTO Vote (restaurant_id, user_id) VALUES (:restaurantId, :userId)", nativeQuery = true)
    int vote(@Param("restaurantId") int restaurantId, @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Vote v WHERE v.user.id =?1 AND v.localDate =?2")
    int deleteVote(int userId, LocalDate date);
}
