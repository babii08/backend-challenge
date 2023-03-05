package com.mvpmatch.backend.adapter.database.user;

import com.mvpmatch.backend.application.domain.users.User;
import com.mvpmatch.backend.application.domain.users.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

@Service
@AllArgsConstructor
public class UserDatabaseService implements UserRepository {

    private UserDatabaseRepository userRepository;
    private ModelMapper modelMapper;

    @Override
    public User saveUser(User user) {
        final var userDBO = userRepository.save(modelMapper.map(user, UserDBO.class));
        return modelMapper.map(userDBO, User.class);
    }

    @Override
    public User findByUserName(String username) {
        final var userDBO = userRepository.findByUserName(username)
                .orElseThrow(() -> new NotFoundException("No user with username: " + username));
        return modelMapper.map(userDBO, User.class);
    }

    @Override
    public User findUser(Long userId) {
        final var userDBO = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user found with id: " + userId));
        return modelMapper.map(userDBO, User.class);
    }

    @Override
    public User removeUser(Long userId) {
        final var userDBO = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("No user found with id: " + userId));
        userRepository.deleteById(userId);
        return modelMapper.map(userDBO, User.class);
    }
}
