package com.tasktracker.task_tracker.mapper;

import com.tasktracker.task_tracker.dto.TaskDto;
import com.tasktracker.task_tracker.entity.Task;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto (Task task);
}
