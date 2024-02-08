package com.event.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return null;
    }

    @PostMapping
    public ResponseEntity<?> createUser() {
        return null;
    }
}
