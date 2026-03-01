package com.github.denisdementevgithub.voteproject.user.web.user;

import com.github.denisdementevgithub.voteproject.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "AdminRestController", description = "Use to interact with the User entities as an ADMIN")
public class AdminRestController extends AbstractUserController {

    public static final String REST_URL = "/api/admin/users";

    @Override
    @GetMapping
    @Operation(summary = "Get all users")
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    @Operation(summary = "Get an user")
    public User get(@PathVariable @Parameter(example = "100002") int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add a new user to the app")
    public ResponseEntity<User> createWithLocation(@Valid @RequestBody User user) {
        User created = super.create(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete an user")
    public void delete(@PathVariable @Parameter(example = "100002") int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Update description of an user")
    public void update(@Valid @RequestBody User user, @PathVariable @Parameter(example = "100002") int id) {
        super.update(user, id);
    }

    @Override
    @GetMapping("/by-email")
    @Operation(summary = "Get an user by e-mail")
    public User getByMail(@RequestParam @Parameter(example = "user@yandex.ru") String email) {
        return super.getByMail(email);
    }

    @Override
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Enable/disable user")
    public void enable(@PathVariable @Parameter(example = "100002") int id, @RequestParam @Parameter(example = "false") boolean enabled) {
        super.enable(id, enabled);
    }
}