package com.cfe.chat.service;

import com.cfe.chat.domain.User;
import com.cfe.chat.exception.DataNotFoundException;
import com.cfe.chat.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUsers() {
        log.debug("getting all users");
        List<User> users = userRepository.findAll();
        log.info("found {} users", users.size());
        return users;
    }

    public User getUser(Long userId) {
        log.debug("getting user by userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + userId));
        log.info("found user: {}", user);
        return user;
    }
}
