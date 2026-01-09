package com.geekmonkey.taskmanagement.domain;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String email;
    private String fullName;
    private String password;
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
    @Builder.Default
    private Set<String> teamIds = new HashSet<>();
    private String localePreference;
    private Instant createdAt;
}
