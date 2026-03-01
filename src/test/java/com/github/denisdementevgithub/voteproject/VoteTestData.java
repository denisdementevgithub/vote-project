package com.github.denisdementevgithub.voteproject;

public class VoteTestData {

    public static final String beforeVoting = """
            {
                    "100004": 1,
                    "100010": 1
            }""";
    public static final String afterVoting = """
            {
                    "100004": 2,
                    "100010": 1
            }""";
    public static final String afterReVoting = """
            {
                    "100004": 1,
                    "100010": 2
            }""";
}

