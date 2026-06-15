package com.tasktracker.task_tracker.service;

import com.tasktracker.task_tracker.dto.TaskDto;
import com.tasktracker.task_tracker.dto.TaskRequest;
import com.tasktracker.task_tracker.dto.TaskUpdateRequest;
import com.tasktracker.task_tracker.entity.Task;
import com.tasktracker.task_tracker.entity.User;
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

    public List<TaskDto> getAllTask( ) {

        User user = userRepository.findByEmail(getCurrentUserEmail()).orElseThrow();
        List<Task> tasks = taskRepository.findAllByUserId(user.getId());
        return tasks.stream()
                .map(this::toDto)
                .toList();
    }

    public TaskDto createTask(TaskRequest request){
        User user = userRepository.findByEmail(getCurrentUserEmail()).orElseThrow();
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDone(false);
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);

        Task saved = taskRepository.save(task);

        return  toDto(saved);
    }

    public TaskDto updateTask(long taskId, TaskUpdateRequest request){
        Task task = taskRepository.findById(taskId).orElseThrow();

        if (!task.getUser().getUsername().equals(getCurrentUserEmail())){
            throw new RuntimeException("Access denied");
        }
        if (request.getTitle() != null){
            task.setTitle(request.getTitle());
        }
        if(request.getDescription() != null){
            task.setDescription(request.getDescription());
        }
        if (request.getDone() != null){
            task.setDone(request.getDone());
        }

        Task saved = taskRepository.save(task);
        return toDto(saved);
    }

    public void deleteTask(long taskId){
        Task task = taskRepository.findById(taskId).orElseThrow();
        if (!task.getUser().getUsername().equals(getCurrentUserEmail())){
            throw new RuntimeException(("Access denied"));
        }
        taskRepository.delete(task);
    }

    private String getCurrentUserEmail(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private TaskDto toDto(Task task){
        TaskDto dto = new TaskDto();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDone(task.getDone());
        dto.setCreatedAt(task.getCreatedAt());
        return  dto;
    }
}
