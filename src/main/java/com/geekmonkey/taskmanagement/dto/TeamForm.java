package com.geekmonkey.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class TeamForm {
    @NotBlank
    private String name;
    private String description;
    private String leadId;
    private Set<String> memberIds = new HashSet<>();
}
