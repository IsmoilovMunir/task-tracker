package com.tasktracker.task_tracker.controller;

import com.tasktracker.task_tracker.dto.LoginRequest;
import com.tasktracker.task_tracker.dto.RegisterRequest;
import com.tasktracker.task_tracker.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody RegisterRequest request){
        authService.register(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){
        String token = authService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

}
