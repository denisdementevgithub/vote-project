package com.github.denisdementevgithub.voteproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VoteApplication {
    public static void main(String[] args) {
        SpringApplication.run(VoteApplication.class, args);
    }
}