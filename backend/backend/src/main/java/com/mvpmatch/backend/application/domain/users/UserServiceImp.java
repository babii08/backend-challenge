package com.mvpmatch.backend.application.domain.users;

import com.mvpmatch.backend.adapter.database.user.Role;
import com.mvpmatch.backend.adapter.http.users.UserRequest;
import com.mvpmatch.backend.adapter.http.users.UserService;
import com.mvpmatch.backend.application.domain.JWTService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;


    public User findUserByName(String username) {
        return modelMapper.map(userRepository.findByUserName(username), User.class);
    }

    public User registerUser(User userData) {
        userData.setPassword(passwordEncoder.encode(userData.getPassword()));
        final var user = userRepository.saveUser(userData);
        if (userData.getRoles().contains(Role.SELLER)) {
            user.setToken(generateTokenBasedOn(user));
        }
        return user;
    }

    public User getUser(Long userId) {
        return userRepository.findUser(userId);
    }

    public String authenticateUser(User userData) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userData.getUserName(), userData.getPassword())
        );
        var user = userRepository.findByUserName(userData.getUserName());
        return generateTokenBasedOn(user);
    }

    private String generateTokenBasedOn(final User user) {
        final var authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole()))
                .collect(Collectors.toList());
        return jwtService.generateToken(
                new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), authorities)
        );
    }

    public User updateUser(Long userId, UserRequest userRequest) {
        final var user = userRepository.findUser(userId);
        var userUpdated = modelMapper.map(user, User.class);
        userUpdated.setPassword(passwordEncoder.encode(userRequest.password()));
        userUpdated = userRepository.saveUser(userUpdated);
        if (userUpdated.getRoles().contains(Role.SELLER)) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userUpdated.getUserName(), userUpdated.getPassword())
            );
            userUpdated.setToken(generateTokenBasedOn(userUpdated));
        }
        return userUpdated;
    }

    public User removeUser(Long userId) {
        return userRepository.removeUser(userId);
    }
}
