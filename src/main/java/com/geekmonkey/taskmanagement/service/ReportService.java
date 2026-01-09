package com.geekmonkey.taskmanagement.service;

import com.geekmonkey.taskmanagement.domain.Task;
import com.geekmonkey.taskmanagement.domain.TaskStatus;
import com.geekmonkey.taskmanagement.domain.TimeLog;
import com.geekmonkey.taskmanagement.dto.SprintReport;
import com.geekmonkey.taskmanagement.repository.CarryoverRecordRepository;
import com.geekmonkey.taskmanagement.repository.TaskRepository;
import com.geekmonkey.taskmanagement.repository.TimeLogRepository;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final TaskRepository taskRepository;
    private final TimeLogRepository timeLogRepository;
    private final CarryoverRecordRepository carryoverRecordRepository;

    public SprintReport buildSprintReport(String sprintId) {
        List<Task> tasks = taskRepository.findBySprintId(sprintId);
        int total = tasks.size();
        int completed = (int) tasks.stream().filter(task -> task.getStatus() == TaskStatus.DONE).count();
        int carryover = carryoverRecordRepository.findByFromSprintId(sprintId).size();
        double progress = total == 0 ? 0 : (completed * 100.0) / total;
        List<Task> blocked = tasks.stream().filter(task -> task.getStatus() == TaskStatus.BLOCKED).toList();
        Map<String, Integer> minutesByUser = timeLogRepository.findAll().stream()
            .filter(log -> tasks.stream().anyMatch(task -> task.getId().equals(log.getTaskId())))
            .collect(Collectors.groupingBy(TimeLog::getUserId, Collectors.summingInt(TimeLog::getMinutes)));
        List<SprintReport.UserHours> topUsers = minutesByUser.entrySet().stream()
            .map(entry -> SprintReport.UserHours.builder()
                .userId(entry.getKey())
                .minutes(entry.getValue())
                .build())
            .sorted(Comparator.comparingInt(SprintReport.UserHours::getMinutes).reversed())
            .limit(5)
            .toList();
        List<Task> doneTasks = tasks.stream().filter(task -> task.getStatus() == TaskStatus.DONE).toList();
        long avgCycle = doneTasks.isEmpty() ? 0 : (long) doneTasks.stream()
            .mapToLong(task -> Duration.between(task.getCreatedAt(), task.getUpdatedAt()).toMinutes())
            .average()
            .orElse(0);
        return SprintReport.builder()
            .totalTasks(total)
            .completedTasks(completed)
            .carryoverTasks(carryover)
            .progressPercent(progress)
            .blockedTasks(blocked)
            .topUsers(topUsers)
            .averageCycleMinutes(avgCycle)
            .build();
    }
}
