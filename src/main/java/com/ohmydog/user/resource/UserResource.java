package com.ohmydog.user.resource;

import com.ohmydog.user.dto.UserDTO;
import com.ohmydog.user.entity.User;
import com.ohmydog.user.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserResource {

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> user = userService.findAll();
        return ResponseEntity.ok(user.stream().map(UserDTO::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable("id") UUID uuid) {
        User user = userService.findById(uuid);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDTO> createUser(@RequestBody User requestBodyUser, UriComponentsBuilder uriBuilder) {
        User user = userService.createUser(requestBodyUser);
        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(new UserDTO(user));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<UserDTO> updateUser(@RequestBody User requestBodyUser) {
        User user = userService.updateUser(requestBodyUser);
        return ResponseEntity.ok(new UserDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID uuid) {
        userService.deleteUser(uuid);
        return ResponseEntity.noContent().build();
    }
}
