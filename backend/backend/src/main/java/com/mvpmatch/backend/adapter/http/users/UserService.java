package com.mvpmatch.backend.adapter.http.users;

import com.mvpmatch.backend.adapter.database.user.Role;
import com.mvpmatch.backend.application.domain.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.stream.Collectors;

public interface UserService {

    User findUserByName(String username);

    User registerUser(User userData);

    User getUser(Long userId);

    String authenticateUser(User userData);

    User updateUser(Long userId, UserRequest userRequest);

    User removeUser(Long userId);
}
