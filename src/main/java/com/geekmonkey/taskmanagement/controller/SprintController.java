package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.domain.User;
import com.geekmonkey.taskmanagement.dto.SprintForm;
import com.geekmonkey.taskmanagement.repository.TaskRepository;
import com.geekmonkey.taskmanagement.service.SprintService;
import com.geekmonkey.taskmanagement.util.SecurityUtil;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SprintController {
    private final SprintService sprintService;
    private final SecurityUtil securityUtil;
    private final TaskRepository taskRepository;

    @GetMapping("/sprints")
    public String sprints(Model model) {
        User user = securityUtil.getCurrentUser().orElseThrow();
        String teamId = user.getTeamIds().stream().findFirst().orElse(null);
        var sprints = teamId == null ? java.util.List.of() : sprintService.findByTeam(teamId);
        model.addAttribute("sprints", sprints);
        Map<String, Object> tasksBySprint = new HashMap<>();
        sprints.forEach(sprint -> tasksBySprint.put(sprint.getId(), taskRepository.findBySprintId(sprint.getId())));
        model.addAttribute("tasksBySprint", tasksBySprint);
        SprintForm form = new SprintForm();
        form.setTeamId(teamId);
        model.addAttribute("sprintForm", form);
        return "sprints/list";
    }

    @PostMapping("/sprints")
    public String createSprint(@Valid SprintForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("sprints", sprintService.findByTeam(form.getTeamId()));
            return "sprints/list";
        }
        sprintService.create(form);
        return "redirect:/sprints";
    }

    @PostMapping("/sprints/{id}/start")
    public String startSprint(@PathVariable String id) {
        sprintService.startSprint(id);
        return "redirect:/sprints";
    }

    @PostMapping("/sprints/{id}/close")
    public String closeSprint(@PathVariable String id, @RequestParam Map<String, String> params) {
        Map<String, String> reasons = new HashMap<>();
        taskRepository.findBySprintId(id).forEach(task -> {
            String value = params.get("carryover_" + task.getId());
            if (value != null) {
                reasons.put(task.getId(), value);
            }
        });
        sprintService.closeSprint(id, reasons);
        return "redirect:/sprints";
    }
}
