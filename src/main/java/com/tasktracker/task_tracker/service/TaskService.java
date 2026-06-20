package com.tasktracker.task_tracker.service;

import com.tasktracker.task_tracker.dto.TaskDto;
import com.tasktracker.task_tracker.dto.TaskRequest;
import com.tasktracker.task_tracker.dto.TaskUpdateRequest;
import com.tasktracker.task_tracker.entity.Task;
import com.tasktracker.task_tracker.entity.User;
import com.tasktracker.task_tracker.exception.AccessDeniedException;
import com.tasktracker.task_tracker.exception.ResourceNotFoundException;
import com.tasktracker.task_tracker.mapper.TaskMapper;
import com.tasktracker.task_tracker.repository.TaskRepository;
import com.tasktracker.task_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    public List<TaskDto> getAllTask() {

        User user = userRepository.findByEmail(getCurrentUserEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
    }

    public TaskDto createTask(TaskRequest request) {
        User user = userRepository.findByEmail(getCurrentUserEmail()).orElseThrow(() ->
                new ResourceNotFoundException("User not found"));
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDone(false);
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);
        task.setUpdateAt(LocalDateTime.now());

        Task saved = taskRepository.save(task);

        return taskMapper.toDto(saved);
    }

    public TaskDto updateTask(long taskId, TaskUpdateRequest request) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new ResourceNotFoundException("Task not found"));

        if (!task.getUser().getUsername().equals(getCurrentUserEmail())) {
            throw new AccessDeniedException("Access denied");
        }
        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getDone() != null) {
            task.setDone(request.getDone());
        }
        task.setUpdateAt(LocalDateTime.now());
        Task saved = taskRepository.save(task);
        return taskMapper.toDto(saved);
    }

    public void deleteTask(long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() ->
                new ResourceNotFoundException("Task not found"));
        if (!task.getUser().getUsername().equals(getCurrentUserEmail())) {
            throw new AccessDeniedException(("Access denied"));
        }
        taskRepository.delete(task);
    }

    private String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
