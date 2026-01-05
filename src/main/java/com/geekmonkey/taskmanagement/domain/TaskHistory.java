package com.geekmonkey.taskmanagement.domain;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskHistory {
    private TaskStatus status;
    private Instant changedAt;
    private String changedBy;
    private String reason;
}
