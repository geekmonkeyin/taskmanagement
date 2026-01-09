package com.geekmonkey.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProjectForm {
    @NotBlank
    private String name;
    private String description;
    @NotBlank
    private String teamId;
}
