package com.geekmonkey.taskmanagement.service;

import com.geekmonkey.taskmanagement.domain.Role;
import com.geekmonkey.taskmanagement.domain.Team;
import com.geekmonkey.taskmanagement.domain.User;
import com.geekmonkey.taskmanagement.dto.UserForm;
import com.geekmonkey.taskmanagement.repository.TeamRepository;
import com.geekmonkey.taskmanagement.repository.UserRepository;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(@Valid UserForm form) {
        User user = User.builder()
            .email(form.getEmail())
            .fullName(form.getFullName())
            .password(passwordEncoder.encode(form.getPassword()))
            .roles(form.getRoles())
            .teamIds(form.getTeamIds())
            .createdAt(Instant.now())
            .build();
        User saved = userRepository.save(user);
        syncTeams(saved);
        return saved;
    }

    public void syncTeams(User user) {
        for (Team team : teamRepository.findAll()) {
            if (user.getTeamIds().contains(team.getId())) {
                team.getMemberIds().add(user.getId());
            } else {
                team.getMemberIds().remove(user.getId());
            }
            teamRepository.save(team);
        }
    }

    public boolean isAdmin(User user) {
        return user.getRoles().contains(Role.ADMIN);
    }
}
