package com.geekmonkey.taskmanagement.domain;

import java.time.Instant;
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
@Document(collection = "carryovers")
public class CarryoverRecord {
    @Id
    private String id;
    private String taskId;
    private String fromSprintId;
    private String toSprintId;
    private String reason;
    private Instant createdAt;
}
