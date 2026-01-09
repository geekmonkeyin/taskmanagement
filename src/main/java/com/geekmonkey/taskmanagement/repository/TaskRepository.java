package com.geekmonkey.taskmanagement.repository;

import com.geekmonkey.taskmanagement.domain.Task;
import com.geekmonkey.taskmanagement.domain.TaskStatus;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
    Page<Task> findByTeamIdAndSprintIdIsNull(String teamId, Pageable pageable);
    List<Task> findBySprintId(String sprintId);
    List<Task> findBySprintIdAndStatus(String sprintId, TaskStatus status);
    List<Task> findByTeamIdAndStatus(String teamId, TaskStatus status);
}
