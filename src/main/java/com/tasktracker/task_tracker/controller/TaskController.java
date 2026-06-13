package com.tasktracker.task_tracker.controller;

import com.tasktracker.task_tracker.dto.TaskDto;
import com.tasktracker.task_tracker.dto.TaskRequest;
import com.tasktracker.task_tracker.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping("")
    public ResponseEntity<List<TaskDto>> getAllTasks(){
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody TaskRequest request){
        return ResponseEntity.ok(taskService.createTask(request));
    }
}
