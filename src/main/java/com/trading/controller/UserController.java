package com.trading.controller;

import com.trading.dto.UserRequest;
import com.trading.entity.User;
import com.trading.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping
    public User registerUser(@Valid @RequestBody UserRequest request) {
        return userService.registerUser(request);
    }
}