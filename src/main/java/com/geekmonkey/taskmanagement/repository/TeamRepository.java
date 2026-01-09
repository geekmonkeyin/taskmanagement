package com.geekmonkey.taskmanagement.repository;

import com.geekmonkey.taskmanagement.domain.Team;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeamRepository extends MongoRepository<Team, String> {
}
