package com.geekmonkey.taskmanagement.service;

import com.geekmonkey.taskmanagement.domain.Project;
import com.geekmonkey.taskmanagement.dto.ProjectForm;
import com.geekmonkey.taskmanagement.repository.ProjectRepository;
import jakarta.validation.Valid;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> findByTeam(String teamId) {
        return projectRepository.findByTeamId(teamId);
    }

    public Project create(@Valid ProjectForm form) {
        Project project = Project.builder()
            .name(form.getName())
            .description(form.getDescription())
            .teamId(form.getTeamId())
            .createdAt(Instant.now())
            .build();
        return projectRepository.save(project);
    }
}
