package com.geekmonkey.taskmanagement.dto;

import com.geekmonkey.taskmanagement.domain.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class StatusUpdateForm {
    @NotNull
    private TaskStatus status;
    private String reason;
}
