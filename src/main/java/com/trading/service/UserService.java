package com.trading.service;

import com.trading.dto.UserRequest;
import com.trading.entity.User;
import com.trading.exception.ResourceNotFoundException;
import com.trading.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j

public class UserService {

    private final UserRepository userRepository;

    // Register a new user
    public User registerUser(UserRequest request) {

        log.info("Registering New User");

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .balance(request.getBalance())
                .build();

        User savedUser = userRepository.save(user);

        log.info("User Registered Successfully");

        return savedUser;
    }

    // Get user by ID
    public User getUserById(Long id) {

        log.info("Fetching User By ID: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // Get all users
    public List<User> getAllUsers() {

        log.info("Fetching All Users");

        return userRepository.findAll();
    }

    // Delete user by ID
    public String deleteUser(Long id) {

        log.info("Deleting User By ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);

        log.info("User Deleted Successfully");

        return "User deleted successfully";
    }
}