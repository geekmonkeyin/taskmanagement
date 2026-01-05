package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.domain.User;
import com.geekmonkey.taskmanagement.dto.ProjectForm;
import com.geekmonkey.taskmanagement.service.ProjectService;
import com.geekmonkey.taskmanagement.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
    private final SecurityUtil securityUtil;

    @GetMapping("/projects")
    public String projects(Model model) {
        User user = securityUtil.getCurrentUser().orElseThrow();
        String teamId = user.getTeamIds().stream().findFirst().orElse(null);
        model.addAttribute("projects", teamId == null ? java.util.List.of() : projectService.findByTeam(teamId));
        ProjectForm form = new ProjectForm();
        form.setTeamId(teamId);
        model.addAttribute("projectForm", form);
        return "projects/list";
    }

    @PostMapping("/projects")
    public String createProject(@Valid ProjectForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("projects", projectService.findByTeam(form.getTeamId()));
            return "projects/list";
        }
        projectService.create(form);
        return "redirect:/projects";
    }
}
