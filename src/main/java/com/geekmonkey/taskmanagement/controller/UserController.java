package com.geekmonkey.taskmanagement.controller;

import com.geekmonkey.taskmanagement.dto.UserForm;
import com.geekmonkey.taskmanagement.repository.TeamRepository;
import com.geekmonkey.taskmanagement.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TeamRepository teamRepository;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("userForm", new UserForm());
        return "users/list";
    }

    @PostMapping("/users")
    public String createUser(@Valid UserForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("users", userService.findAll());
            model.addAttribute("teams", teamRepository.findAll());
            return "users/list";
        }
        userService.create(form);
        return "redirect:/users";
    }
}
