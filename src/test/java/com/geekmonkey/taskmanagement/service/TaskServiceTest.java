package com.geekmonkey.taskmanagement.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.geekmonkey.taskmanagement.domain.Task;
import com.geekmonkey.taskmanagement.domain.TaskStatus;
import com.geekmonkey.taskmanagement.repository.TaskRepository;
import com.geekmonkey.taskmanagement.repository.TimeLogRepository;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TaskServiceTest {
    @Test
    void updateStatusRequiresReasonWhenBlocked() {
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        TimeLogRepository timeLogRepository = Mockito.mock(TimeLogRepository.class);
        TaskService service = new TaskService(taskRepository, timeLogRepository);

        Task task = Task.builder().id("1").status(TaskStatus.TODO).build();
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        assertThatThrownBy(() -> service.updateStatus(task, TaskStatus.BLOCKED, "", "user", false))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateStatusRequiresTimeLogForDone() {
        TaskRepository taskRepository = Mockito.mock(TaskRepository.class);
        TimeLogRepository timeLogRepository = Mockito.mock(TimeLogRepository.class);
        TaskService service = new TaskService(taskRepository, timeLogRepository);

        Task task = Task.builder().id("2").status(TaskStatus.IN_PROGRESS).build();
        when(timeLogRepository.findByTaskId("2")).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> service.updateStatus(task, TaskStatus.DONE, null, "user", false))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
