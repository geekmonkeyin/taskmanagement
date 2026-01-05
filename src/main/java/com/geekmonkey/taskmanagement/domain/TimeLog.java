package com.geekmonkey.taskmanagement.domain;

import java.time.Instant;
import java.time.LocalDate;
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
@Document(collection = "time_logs")
public class TimeLog {
    @Id
    private String id;
    private String taskId;
    private String userId;
    private LocalDate logDate;
    private Integer minutes;
    private String notes;
    private Instant createdAt;
}
