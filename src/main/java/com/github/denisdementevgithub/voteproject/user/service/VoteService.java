package com.github.denisdementevgithub.voteproject.user.service;

import com.github.denisdementevgithub.voteproject.common.error.IllegalRequestDataException;
import com.github.denisdementevgithub.voteproject.user.model.User;
import com.github.denisdementevgithub.voteproject.user.model.Vote;
import com.github.denisdementevgithub.voteproject.user.repository.VoteRepository;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Setter
public class VoteService {

    private final VoteRepository repository;
    private Clock clock;
    private final LocalTime finalTime = LocalTime.of(11, 0);

    public VoteService(VoteRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    public Map<Integer, Integer> getAllForToday() {
        return repository.getAllByLocalDate(LocalDate.now()).stream()
                .collect(Collectors.groupingBy(
                        row -> (Integer) row[0],
                        Collectors.mapping(
                                row -> (Integer) row[1],
                                Collectors.toList()
                        )
                )).entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, i -> i.getValue().size()));
    }

    @Transactional
    public Vote save(Vote vote) {
        if (repository.getByUserAndLocalDate(getFakeUser(vote.getUser().getId()), LocalDate.now()) != null &&
                LocalTime.now(clock).isAfter(finalTime)) {
            throw new IllegalRequestDataException("Вы уже голосовали");
        } else {
            return repository.save(vote);
        }
    }

    @Transactional
    public Vote update(Vote vote) {
        Vote voteFromDb = repository.getByUserAndLocalDate(getFakeUser(vote.getUser().getId()), LocalDate.now());
        vote.setId(voteFromDb.id());
        vote.setLocalDate(voteFromDb.getLocalDate());
        if (LocalTime.now(clock).isAfter(finalTime)) {
            throw new IllegalRequestDataException("Переголосовать можно до " + finalTime);
        } else {
            return repository.save(vote);
        }
    }

    public static User getFakeUser(int id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
