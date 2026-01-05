package com.geekmonkey.taskmanagement.dto;

import com.geekmonkey.taskmanagement.domain.Task;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SprintReport {
    private int totalTasks;
    private int completedTasks;
    private int carryoverTasks;
    private double progressPercent;
    private List<Task> blockedTasks;
    private List<UserHours> topUsers;
    private long averageCycleMinutes;

    @Data
    @Builder
    public static class UserHours {
        private String userId;
        private int minutes;
    }
}
