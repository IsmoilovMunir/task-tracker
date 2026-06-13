package com.tasktracker.task_tracker.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private long id;
    private String title;
    private String description;
    private Boolean done;
    private LocalDateTime createdAt;
}
