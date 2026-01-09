package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.domain.Task;
import com.geekmonkey.taskmanagement.dto.TimeLogForm;
import com.geekmonkey.taskmanagement.repository.TimeLogRepository;
import com.geekmonkey.taskmanagement.service.TaskService;
import com.geekmonkey.taskmanagement.service.TimeLogService;
import com.geekmonkey.taskmanagement.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TimeLogService timeLogService;
    private final TimeLogRepository timeLogRepository;
    private final SecurityUtil securityUtil;

    @GetMapping("/tasks/{id}")
    public String taskDetail(@PathVariable String id, Model model) {
        Task task = taskService.getById(id);
        model.addAttribute("task", task);
        TimeLogForm form = new TimeLogForm();
        form.setTaskId(id);
        model.addAttribute("timeLogForm", form);
        model.addAttribute("logs", timeLogRepository.findByTaskId(id));
        return "tasks/detail";
    }

    @PostMapping("/tasks/{id}/time")
    public String logTime(@PathVariable String id, @Valid TimeLogForm form, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/tasks/" + id + "?error";
        }
        String userId = securityUtil.getCurrentUser().orElseThrow().getId();
        timeLogService.logTime(form, userId);
        return "redirect:/tasks/" + id;
    }
}
