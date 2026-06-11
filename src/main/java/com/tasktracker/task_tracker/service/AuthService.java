package com.tasktracker.task_tracker.service;

import com.tasktracker.task_tracker.dto.RegisterRequest;
import com.tasktracker.task_tracker.entity.User;
import com.tasktracker.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

        public void register (RegisterRequest registerRequest){
            if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()){
                throw  new RuntimeException("Email already exists");
            }
            User user = new User();
            user.setEmail(registerRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
        }

    }
