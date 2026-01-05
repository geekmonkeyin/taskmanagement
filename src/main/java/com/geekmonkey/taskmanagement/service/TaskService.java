package com.geekmonkey.taskmanagement.service;

import com.geekmonkey.taskmanagement.domain.Role;
import com.geekmonkey.taskmanagement.domain.Task;
import com.geekmonkey.taskmanagement.domain.TaskHistory;
import com.geekmonkey.taskmanagement.domain.TaskStatus;
import com.geekmonkey.taskmanagement.dto.TaskForm;
import com.geekmonkey.taskmanagement.repository.TaskRepository;
import com.geekmonkey.taskmanagement.repository.TimeLogRepository;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TimeLogRepository timeLogRepository;

    public Task create(@Valid TaskForm form) {
        Task task = Task.builder()
            .title(form.getTitle())
            .description(form.getDescription())
            .teamId(form.getTeamId())
            .projectId(form.getProjectId())
            .sprintId(form.getSprintId())
            .assigneeId(form.getAssigneeId())
            .reporterId(form.getReporterId())
            .status(form.getStatus())
            .priority(form.getPriority())
            .type(form.getType())
            .estimateMinutes(form.getEstimateMinutes())
            .tags(form.getTags())
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .build();
        task.getHistory().add(TaskHistory.builder()
            .status(task.getStatus())
            .changedAt(Instant.now())
            .changedBy(form.getReporterId())
            .reason("Created")
            .build());
        return taskRepository.save(task);
    }

    public Page<Task> getBacklog(String teamId, Pageable pageable) {
        return taskRepository.findByTeamIdAndSprintIdIsNull(teamId, pageable);
    }

    public List<Task> getBySprint(String sprintId) {
        return taskRepository.findBySprintId(sprintId);
    }

    public Task getById(String id) {
        return taskRepository.findById(id).orElseThrow();
    }

    public Task updateStatus(Task task, TaskStatus status, String reason, String userId, boolean managerOverride) {
        if (status == TaskStatus.BLOCKED && (reason == null || reason.isBlank())) {
            throw new IllegalArgumentException("Blocked reason required");
        }
        if (status == TaskStatus.DONE && !managerOverride && timeLogRepository.findByTaskId(task.getId()).isEmpty()) {
            throw new IllegalArgumentException("Log time before completing the task");
        }
        task.setStatus(status);
        task.setBlockedReason(status == TaskStatus.BLOCKED ? reason : null);
        task.setUpdatedAt(Instant.now());
        task.getHistory().add(TaskHistory.builder()
            .status(status)
            .changedAt(Instant.now())
            .changedBy(userId)
            .reason(reason)
            .build());
        return taskRepository.save(task);
    }

    public boolean isManagerOrAdmin(List<Role> roles) {
        return roles.contains(Role.MANAGER) || roles.contains(Role.ADMIN);
    }
}
