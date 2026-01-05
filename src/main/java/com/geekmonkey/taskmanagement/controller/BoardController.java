package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.domain.Role;
import com.geekmonkey.taskmanagement.domain.Task;
import com.geekmonkey.taskmanagement.dto.StatusUpdateForm;
import com.geekmonkey.taskmanagement.service.TaskService;
import com.geekmonkey.taskmanagement.util.SecurityUtil;
import jakarta.validation.Valid;
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
public class BoardController {
    private final TaskService taskService;
    private final SecurityUtil securityUtil;

    @GetMapping("/board")
    public String board(@RequestParam(required = false) String sprintId, Model model) {
        model.addAttribute("tasks", sprintId == null ? java.util.List.of() : taskService.getBySprint(sprintId));
        model.addAttribute("sprintId", sprintId);
        model.addAttribute("statusForm", new StatusUpdateForm());
        return "board/kanban";
    }

    @PostMapping("/board/{taskId}/status")
    public String updateStatus(@PathVariable String taskId,
                               @RequestParam String sprintId,
                               @Valid StatusUpdateForm form,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/board?sprintId=" + sprintId + "&error";
        }
        Task task = taskService.getById(taskId);
        var user = securityUtil.getCurrentUser().orElseThrow();
        boolean managerOverride = user.getRoles().contains(Role.MANAGER) || user.getRoles().contains(Role.ADMIN);
        taskService.updateStatus(task, form.getStatus(), form.getReason(), user.getId(), managerOverride);
        return "redirect:/board?sprintId=" + sprintId;
    }
}
