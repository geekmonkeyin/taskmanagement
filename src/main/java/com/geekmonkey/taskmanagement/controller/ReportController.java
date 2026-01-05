package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/reports/sprint")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public String sprintReport(@RequestParam String sprintId, Model model) {
        model.addAttribute("report", reportService.buildSprintReport(sprintId));
        model.addAttribute("sprintId", sprintId);
        return "reports/sprint";
    }
}
