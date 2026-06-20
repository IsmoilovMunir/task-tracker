package com.tasktracker.task_tracker.service;

import com.tasktracker.task_tracker.dto.UserDto;
import com.tasktracker.task_tracker.entity.User;
import com.tasktracker.task_tracker.exception.ResourceNotFoundException;
import com.tasktracker.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()-> new ResourceNotFoundException("User not found"));
        return new UserDto(user.getId(), user.getEmail());
    }
}
