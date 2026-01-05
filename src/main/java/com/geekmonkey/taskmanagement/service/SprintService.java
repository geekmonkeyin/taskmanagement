package com.geekmonkey.taskmanagement.service;

import com.geekmonkey.taskmanagement.domain.CarryoverRecord;
import com.geekmonkey.taskmanagement.domain.Sprint;
import com.geekmonkey.taskmanagement.domain.SprintStatus;
import com.geekmonkey.taskmanagement.domain.Task;
import com.geekmonkey.taskmanagement.domain.TaskStatus;
import com.geekmonkey.taskmanagement.dto.SprintForm;
import com.geekmonkey.taskmanagement.repository.CarryoverRecordRepository;
import com.geekmonkey.taskmanagement.repository.SprintRepository;
import com.geekmonkey.taskmanagement.repository.TaskRepository;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SprintService {
    private final SprintRepository sprintRepository;
    private final TaskRepository taskRepository;
    private final CarryoverRecordRepository carryoverRecordRepository;

    public List<Sprint> findByTeam(String teamId) {
        return sprintRepository.findByTeamId(teamId);
    }

    public Sprint create(@Valid SprintForm form) {
        Sprint sprint = Sprint.builder()
            .name(form.getName())
            .startDate(form.getStartDate())
            .endDate(form.getEndDate())
            .goal(form.getGoal())
            .teamId(form.getTeamId())
            .status(SprintStatus.PLANNED)
            .createdAt(Instant.now())
            .build();
        return sprintRepository.save(sprint);
    }

    public Sprint startSprint(String sprintId) {
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow();
        sprint.setStatus(SprintStatus.ACTIVE);
        return sprintRepository.save(sprint);
    }

    public Sprint closeSprint(String sprintId, Map<String, String> carryoverReasons) {
        Sprint sprint = sprintRepository.findById(sprintId).orElseThrow();
        List<Task> tasks = taskRepository.findBySprintId(sprintId);
        for (Task task : tasks) {
            if (task.getStatus() != TaskStatus.DONE) {
                String reason = carryoverReasons.get(task.getId());
                if (reason == null || reason.isBlank()) {
                    throw new IllegalArgumentException("Carryover reason required for task " + task.getId());
                }
                task.setSprintId(null);
                task.setCarryoverReason(reason);
                taskRepository.save(task);
                carryoverRecordRepository.save(CarryoverRecord.builder()
                    .taskId(task.getId())
                    .fromSprintId(sprintId)
                    .toSprintId(null)
                    .reason(reason)
                    .createdAt(Instant.now())
                    .build());
            }
        }
        sprint.setStatus(SprintStatus.CLOSED);
        return sprintRepository.save(sprint);
    }
}
