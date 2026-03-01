package com.github.denisdementevgithub.voteproject.user.web.vote;

import com.github.denisdementevgithub.voteproject.user.service.VoteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = VoteController.URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    public static final String URL = "/api/votes";

    @Autowired
    private VoteService service;

    @GetMapping
    public Map<Integer, Integer> getAllForToday() {
        log.info("getAllForToday");
        return service.getAllForToday();
    }
}
