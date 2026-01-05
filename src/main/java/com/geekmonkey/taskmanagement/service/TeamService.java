package com.geekmonkey.taskmanagement.service;

import com.geekmonkey.taskmanagement.domain.Team;
import com.geekmonkey.taskmanagement.dto.TeamForm;
import com.geekmonkey.taskmanagement.repository.TeamRepository;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team create(@Valid TeamForm form) {
        Team team = Team.builder()
            .name(form.getName())
            .description(form.getDescription())
            .leadId(form.getLeadId())
            .memberIds(form.getMemberIds())
            .createdAt(Instant.now())
            .build();
        return teamRepository.save(team);
    }
}
