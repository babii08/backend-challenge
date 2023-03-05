package com.mvpmatch.backend.adapter.http.users;

import com.mvpmatch.backend.application.domain.users.User;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserResource {

    private final UserService usersService;
    private final ModelMapper modelMapper;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userData,
                                                     UriComponentsBuilder uriComponentsBuilder) {
        final var savedUser = usersService.registerUser(modelMapper.map(userData, User.class));
        final var userResponse = new UserResponse(savedUser.getId(), savedUser.getUserName(), savedUser.getToken());
        final var uri = uriComponentsBuilder
                .path("/{userId}")
                .buildAndExpand(userResponse.id())
                .toUri();
        return ResponseEntity
                .created(uri)
                .body(userResponse);
    }


    @PostMapping(value = "/login")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserResponse> authenticateUser(@RequestBody UserRequest userData,
                                                         UriComponentsBuilder uriComponentsBuilder) {
        final var user = modelMapper.map(userData, User.class);
        final var userResponse = new UserResponse(user.getId(), user.getUserName(), user.getToken());
        final var uri = uriComponentsBuilder
                .path("/{userId}")
                .buildAndExpand(userResponse.id())
                .toUri();
        return ResponseEntity
                .created(uri)
                .body(userResponse);
    }

    @PutMapping("/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserResponse> updateUser(Long userId, UserRequest userRequest) {
        final var user = usersService.updateUser(userId, userRequest);
        final var userResponse = new UserResponse(user.getId(), user.getUserName(), user.getToken());
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserResponse> getUser(Long userId) {
        final var user = usersService.getUser(userId);
        final var userResponse = new UserResponse(user.getId(), user.getUserName(), user.getToken());
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("/{userId}")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<UserResponse> removeUser(Long userId) {
        final var user = usersService.removeUser(userId);
        final var userResponse = new UserResponse(user.getId(), user.getUserName(), user.getToken());
        return ResponseEntity.ok(userResponse);
    }

}