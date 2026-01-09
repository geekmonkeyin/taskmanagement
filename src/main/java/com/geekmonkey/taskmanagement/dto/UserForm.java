package com.geekmonkey.taskmanagement.dto;

import com.geekmonkey.taskmanagement.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;

@Data
public class UserForm {
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String fullName;
    @NotBlank
    private String password;
    @NotEmpty
    private Set<Role> roles = new HashSet<>();
    private Set<String> teamIds = new HashSet<>();
}
