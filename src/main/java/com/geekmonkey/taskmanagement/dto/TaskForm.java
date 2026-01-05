package com.geekmonkey.taskmanagement.dto;

import com.geekmonkey.taskmanagement.domain.TaskPriority;
import com.geekmonkey.taskmanagement.domain.TaskStatus;
import com.geekmonkey.taskmanagement.domain.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class TaskForm {
    @NotBlank
    private String title;
    private String description;
    @NotBlank
    private String teamId;
    private String projectId;
    private String sprintId;
    private String assigneeId;
    private String reporterId;
    @NotNull
    private TaskStatus status;
    @NotNull
    private TaskPriority priority;
    @NotNull
    private TaskType type;
    private Integer estimateMinutes;
    private List<String> tags = new ArrayList<>();
}
