package com.tasktracker.task_tracker.controller;

import com.tasktracker.task_tracker.dto.UserDto;
import com.tasktracker.task_tracker.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping()
    public ResponseEntity<UserDto> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }
}
