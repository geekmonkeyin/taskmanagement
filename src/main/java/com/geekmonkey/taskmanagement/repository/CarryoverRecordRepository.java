package com.geekmonkey.taskmanagement.repository;

import com.geekmonkey.taskmanagement.domain.CarryoverRecord;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarryoverRecordRepository extends MongoRepository<CarryoverRecord, String> {
    List<CarryoverRecord> findByFromSprintId(String fromSprintId);
}
