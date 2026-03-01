package com.github.denisdementevgithub.voteproject;

public class VoteTestData {

    public static final String beforeVoting = "{\n" +
            "        \"100004\": 1,\n" +
            "            \"100010\": 1\n" +
            "    }";
    public static final String afterVoting = "{\n" +
            "        \"100004\": 2,\n" +
            "            \"100010\": 1\n" +
            "    }";
    public static final String afterReVoting = "{\n" +
            "        \"100004\": 1,\n" +
            "            \"100010\": 2\n" +
            "    }";
}

