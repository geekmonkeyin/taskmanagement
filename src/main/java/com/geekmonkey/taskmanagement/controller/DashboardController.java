package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.domain.TaskStatus;
import com.geekmonkey.taskmanagement.domain.User;
import com.geekmonkey.taskmanagement.repository.TaskRepository;
import com.geekmonkey.taskmanagement.repository.TeamRepository;
import com.geekmonkey.taskmanagement.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final SecurityUtil securityUtil;
    private final TaskRepository taskRepository;
    private final TeamRepository teamRepository;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        User user = securityUtil.getCurrentUser().orElseThrow();
        String teamId = user.getTeamIds().stream().findFirst().orElse(null);
        model.addAttribute("user", user);
        model.addAttribute("team", teamId == null ? null : teamRepository.findById(teamId).orElse(null));
        model.addAttribute("blockedTasks", teamId == null ? 0 : taskRepository.findByTeamIdAndStatus(teamId, TaskStatus.BLOCKED).size());
        model.addAttribute("inProgressTasks", teamId == null ? 0 : taskRepository.findByTeamIdAndStatus(teamId, TaskStatus.IN_PROGRESS).size());
        model.addAttribute("doneTasks", teamId == null ? 0 : taskRepository.findByTeamIdAndStatus(teamId, TaskStatus.DONE).size());
        return "dashboard";
    }
}
