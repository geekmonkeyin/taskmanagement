package com.geekmonkey.taskmanagement.repository;

import com.geekmonkey.taskmanagement.domain.TimeLog;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TimeLogRepository extends MongoRepository<TimeLog, String> {
    List<TimeLog> findByUserIdAndLogDateBetween(String userId, LocalDate start, LocalDate end);
    List<TimeLog> findByTaskId(String taskId);
}
