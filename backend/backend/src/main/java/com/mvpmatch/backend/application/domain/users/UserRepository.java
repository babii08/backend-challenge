package com.mvpmatch.backend.application.domain.users;

public interface UserRepository {

    User saveUser(User user);

    User findByUserName(String username);

    User findUser(Long userId);

    User removeUser(Long userId);
}
