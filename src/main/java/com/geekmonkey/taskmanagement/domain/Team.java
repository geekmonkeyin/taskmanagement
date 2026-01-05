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
@Document(collection = "teams")
public class Team {
    @Id
    private String id;
    private String name;
    private String description;
    private String leadId;
    @Builder.Default
    private Set<String> memberIds = new HashSet<>();
    private Instant createdAt;
}
