package com.github.denisdementevgithub.voteproject;

import com.github.denisdementevgithub.voteproject.user.model.Vote;

import java.time.LocalDate;
import java.util.List;

public class VoteTestData {

    public static final Vote VOTE_1 = new Vote(100011,RestaurantTestData.restaurant2, UserTestData.user, LocalDate.now());
    public static final Vote VOTE_2 = new Vote(100012,RestaurantTestData.restaurant3, UserTestData.user1, LocalDate.now());
    public static final Vote VOTE_3 = new Vote(100013,RestaurantTestData.restaurant4, UserTestData.user1, LocalDate.now());
    public static final Vote VOTE_4 = new Vote(100014,RestaurantTestData.restaurant5, UserTestData.user1, LocalDate.now());
    public static final Vote VOTE_5 = new Vote(100015,RestaurantTestData.restaurant6, UserTestData.admin, LocalDate.now());

    public static final List<Vote> VOTE_LIST = List.of(VOTE_1, VOTE_2, VOTE_3, VOTE_4, VOTE_5);
}
