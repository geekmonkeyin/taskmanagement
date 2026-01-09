package com.geekmonkey.taskmanagement.repository;

import com.geekmonkey.taskmanagement.domain.Sprint;
import com.geekmonkey.taskmanagement.domain.SprintStatus;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SprintRepository extends MongoRepository<Sprint, String> {
    List<Sprint> findByTeamId(String teamId);
    List<Sprint> findByTeamIdAndStatus(String teamId, SprintStatus status);
}
