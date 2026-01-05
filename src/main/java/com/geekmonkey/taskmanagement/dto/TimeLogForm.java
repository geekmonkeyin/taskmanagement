package com.geekmonkey.taskmanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class TimeLogForm {
    @NotNull
    private String taskId;
    @NotNull
    private LocalDate logDate;
    @NotNull
    @Min(1)
    private Integer minutes;
    private String notes;
}
