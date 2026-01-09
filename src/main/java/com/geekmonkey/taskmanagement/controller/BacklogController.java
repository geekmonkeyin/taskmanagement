package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.domain.User;
import com.geekmonkey.taskmanagement.dto.TaskForm;
import com.geekmonkey.taskmanagement.repository.ProjectRepository;
import com.geekmonkey.taskmanagement.service.TaskService;
import com.geekmonkey.taskmanagement.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BacklogController {
    private final TaskService taskService;
    private final SecurityUtil securityUtil;
    private final ProjectRepository projectRepository;

    @GetMapping("/backlog")
    public String backlog(Model model, Pageable pageable) {
        User user = securityUtil.getCurrentUser().orElseThrow();
        String teamId = user.getTeamIds().stream().findFirst().orElse(null);
        Page<?> tasks = teamId == null ? Page.empty(pageable) : taskService.getBacklog(teamId, pageable);
        model.addAttribute("tasks", tasks);
        model.addAttribute("projects", teamId == null ? java.util.List.of() : projectRepository.findByTeamId(teamId));
        TaskForm form = new TaskForm();
        form.setTeamId(teamId);
        form.setReporterId(user.getId());
        model.addAttribute("taskForm", form);
        return "backlog/list";
    }

    @PostMapping("/backlog")
    public String createTask(@Valid TaskForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tasks", taskService.getBacklog(form.getTeamId(), Pageable.unpaged()));
            model.addAttribute("projects", projectRepository.findByTeamId(form.getTeamId()));
            return "backlog/list";
        }
        taskService.create(form);
        return "redirect:/backlog";
    }
}
