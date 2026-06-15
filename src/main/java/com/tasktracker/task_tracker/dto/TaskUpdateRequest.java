package com.tasktracker.task_tracker.dto;

import lombok.Data;

@Data
public class TaskUpdateRequest {
    private String title;
    private String description;
    private Boolean done;
}
