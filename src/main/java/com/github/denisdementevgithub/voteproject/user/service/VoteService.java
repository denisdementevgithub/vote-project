package com.github.denisdementevgithub.voteproject.user.service;

import com.github.denisdementevgithub.voteproject.user.model.Vote;
import com.github.denisdementevgithub.voteproject.user.repository.MealRepository;
import com.github.denisdementevgithub.voteproject.user.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoteService {

    private final VoteRepository repository;
    private Clock clock;

    public VoteService(VoteRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }
    public Map<Integer, Integer> getVotesForToday() {

        /*LocalDate today = LocalDate.now();
        List<Object[]> listOfObject = repository.getAllForDate(today);
        Map<Integer, Integer> mapOfVotes = new HashMap<>();
        for (Object[] row: listOfObject) {
            int id = (Integer) row[0];
            int count = (Integer) row[1];
            mapOfVotes.put(id, count);
        }
        return mapOfVotes;*/

        return repository.getAllForDate(LocalDate.now()).stream()
                .collect(Collectors.toMap(
                        row->(Integer) row[0],
                        row->(Integer) row[1]
                ));
    }

    public List<Vote> getVotesForTodayForUser(int userId) {
        return repository.getVotesForTodayForUser(userId, LocalDate.now());
    }

    public int vote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        return repository.vote(restaurantId, userId);
    }
    public int revote(int restaurantId, int userId) {
        LocalDate today = LocalDate.now();
        LocalDateTime startDateTime = today.atTime(0, 0);
        LocalDateTime endDateTime = today.atTime(23, 59);
        repository.deleteVote(userId, today);
        return repository.vote(restaurantId, userId);
    }

}
