package ru.javawebinar.topjava.user.web.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import ru.javawebinar.topjava.user.model.User;
import ru.javawebinar.topjava.user.to.UserTo;
//import springfox.documentation.annotations.ApiIgnore;

import jakarta.validation.Valid;
import java.net.URI;

import static ru.javawebinar.topjava.user.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "ProfileRestController", description = "Use to interact with the User entities as an USER")
public class ProfileRestController extends AbstractUserController {
    public static final String REST_URL = "/api/profile";

    @GetMapping
    @Operation(summary = "Get user's info")
    public User get() {
        return super.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete the profile")
    public void delete() {
        super.delete(authUserId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register a new user")
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User created = super.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update user's info")
    public void update(@RequestBody @Valid UserTo userTo) {
        super.update(userTo, authUserId());
    }

    @GetMapping("/text")
    @Operation(hidden = true
    )
    public String testUTF() {
        return "Русский текст";
    }

}