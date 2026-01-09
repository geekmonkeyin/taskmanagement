package com.geekmonkey.taskmanagement.repository;

import com.geekmonkey.taskmanagement.domain.Project;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProjectRepository extends MongoRepository<Project, String> {
    List<Project> findByTeamId(String teamId);
}
