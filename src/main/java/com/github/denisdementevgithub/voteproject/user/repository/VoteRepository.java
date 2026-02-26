package com.github.denisdementevgithub.voteproject.user.repository;

import com.github.denisdementevgithub.voteproject.user.model.User;
import com.github.denisdementevgithub.voteproject.user.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
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
            GROUP BY v.restaurant_id ORDER BY v.restaurant_id ASC
                        """, nativeQuery = true)
    List<Object[]> getAll();

    @Query(value = "SELECT v.restaurant.id, v.user.id FROM Vote v WHERE v.localDate = :date ORDER BY v.id ASC")
    @EntityGraph(attributePaths = {"restaurant", "user"}, type = EntityGraph.EntityGraphType.LOAD)
    List<Object[]> getAllByLocalDate(LocalDate date);

    @Modifying
    @Query(value = "INSERT INTO Vote (restaurant_id, user_id) VALUES (:restaurantId, :userId)", nativeQuery = true)
    int vote(@Param("restaurantId") int restaurantId, @Param("userId") int userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Vote v WHERE v.user.id =?1 AND v.localDate =?2")
    int delete(int userId, LocalDate date);

    Vote getByUserAndLocalDate(User user, LocalDate now);
}
