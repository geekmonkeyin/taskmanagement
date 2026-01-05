package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.service.TimeLogService;
import com.geekmonkey.taskmanagement.util.SecurityUtil;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class TimesheetController {
    private final TimeLogService timeLogService;
    private final SecurityUtil securityUtil;

    @GetMapping("/timesheet")
    public String timesheet(Model model) {
        var user = securityUtil.getCurrentUser().orElseThrow();
        LocalDate today = LocalDate.now();
        LocalDate start = today.with(DayOfWeek.MONDAY);
        LocalDate end = today.with(DayOfWeek.SUNDAY);
        var logs = timeLogService.getWeeklyLogs(user.getId(), start, end);
        Map<LocalDate, Integer> totals = logs.stream()
            .collect(Collectors.groupingBy(log -> log.getLogDate(), Collectors.summingInt(log -> log.getMinutes())));
        model.addAttribute("logs", logs);
        model.addAttribute("totals", totals);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "timesheet/weekly";
    }
}
