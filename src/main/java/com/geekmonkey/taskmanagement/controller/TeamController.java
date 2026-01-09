package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.dto.TeamForm;
import com.geekmonkey.taskmanagement.repository.UserRepository;
import com.geekmonkey.taskmanagement.service.TeamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;
    private final UserRepository userRepository;

    @GetMapping("/teams")
    public String teams(Model model) {
        model.addAttribute("teams", teamService.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("teamForm", new TeamForm());
        return "teams/list";
    }

    @PostMapping("/teams")
    public String createTeam(@Valid TeamForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teams", teamService.findAll());
            model.addAttribute("users", userRepository.findAll());
            return "teams/list";
        }
        teamService.create(form);
        return "redirect:/teams";
    }
}
