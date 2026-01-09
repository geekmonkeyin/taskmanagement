package com.geekmonkey.taskmanagement.service;

import com.geekmonkey.taskmanagement.domain.TimeLog;
import com.geekmonkey.taskmanagement.dto.TimeLogForm;
import com.geekmonkey.taskmanagement.repository.TimeLogRepository;
import jakarta.validation.Valid;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TimeLogService {
    private final TimeLogRepository timeLogRepository;

    public TimeLog logTime(@Valid TimeLogForm form, String userId) {
        TimeLog log = TimeLog.builder()
            .taskId(form.getTaskId())
            .userId(userId)
            .logDate(form.getLogDate())
            .minutes(form.getMinutes())
            .notes(form.getNotes())
            .createdAt(Instant.now())
            .build();
        return timeLogRepository.save(log);
    }

    public List<TimeLog> getWeeklyLogs(String userId, LocalDate start, LocalDate end) {
        return timeLogRepository.findByUserIdAndLogDateBetween(userId, start, end);
    }
}
