package com.tasktracker.task_tracker.service;

import com.tasktracker.task_tracker.dto.EmailEventDto;
import com.tasktracker.task_tracker.dto.LoginRequest;
import com.tasktracker.task_tracker.dto.RegisterRequest;
import com.tasktracker.task_tracker.entity.User;
import com.tasktracker.task_tracker.exception.EmailAlreadyExistsException;
import com.tasktracker.task_tracker.exception.ResourceNotFoundException;
import com.tasktracker.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final KafkaTemplate<String, EmailEventDto> kafkaTemplate;

    public void register(RegisterRequest registerRequest) {
        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        EmailEventDto event = new EmailEventDto(
                user.getEmail(),
                "Welcome!",
                "Спасибо за регистрацию в Task Tracker"
        );
        kafkaTemplate.send("EMAIL_SENDING_TASKS", event);
    }

    public String login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        return jwtService.generateToken(loginRequest.getEmail());
    }
}
