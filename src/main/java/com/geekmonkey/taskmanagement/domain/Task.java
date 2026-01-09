package com.geekmonkey.taskmanagement.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private String teamId;
    private String projectId;
    private String sprintId;
    private String assigneeId;
    private String reporterId;
    private TaskStatus status;
    private TaskPriority priority;
    private TaskType type;
    private Integer estimateMinutes;
    @Builder.Default
    private List<String> tags = new ArrayList<>();
    private String blockedReason;
    private String carryoverReason;
    @Builder.Default
    private List<TaskHistory> history = new ArrayList<>();
    private Instant createdAt;
    private Instant updatedAt;
}
