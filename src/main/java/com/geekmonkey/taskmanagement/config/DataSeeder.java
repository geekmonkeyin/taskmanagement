package com.geekmonkey.taskmanagement.config;

import com.geekmonkey.taskmanagement.domain.Project;
import com.geekmonkey.taskmanagement.domain.Role;
import com.geekmonkey.taskmanagement.domain.Sprint;
import com.geekmonkey.taskmanagement.domain.SprintStatus;
import com.geekmonkey.taskmanagement.domain.Task;
import com.geekmonkey.taskmanagement.domain.TaskPriority;
import com.geekmonkey.taskmanagement.domain.TaskStatus;
import com.geekmonkey.taskmanagement.domain.TaskType;
import com.geekmonkey.taskmanagement.domain.Team;
import com.geekmonkey.taskmanagement.domain.User;
import com.geekmonkey.taskmanagement.repository.ProjectRepository;
import com.geekmonkey.taskmanagement.repository.SprintRepository;
import com.geekmonkey.taskmanagement.repository.TaskRepository;
import com.geekmonkey.taskmanagement.repository.TeamRepository;
import com.geekmonkey.taskmanagement.repository.UserRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataSeeder {
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner seedData(UserRepository userRepository,
                                      TeamRepository teamRepository,
                                      ProjectRepository projectRepository,
                                      SprintRepository sprintRepository,
                                      TaskRepository taskRepository) {
        return args -> {
            if (userRepository.findByEmail("admin@geekmonkey.local").isPresent()) {
                return;
            }

            Team team = teamRepository.save(Team.builder()
                .name("Operations")
                .description("Operations team")
                .createdAt(Instant.now())
                .build());

            User admin = userRepository.save(User.builder()
                .email("admin@geekmonkey.local")
                .fullName("Geekmonkey Admin")
                .password(passwordEncoder.encode("Admin@123"))
                .roles(Set.of(Role.ADMIN))
                .teamIds(Set.of(team.getId()))
                .createdAt(Instant.now())
                .build());

            User manager = userRepository.save(User.builder()
                .email("manager@geekmonkey.local")
                .fullName("Geekmonkey Manager")
                .password(passwordEncoder.encode("Manager@123"))
                .roles(Set.of(Role.MANAGER))
                .teamIds(Set.of(team.getId()))
                .createdAt(Instant.now())
                .build());

            User member = userRepository.save(User.builder()
                .email("member@geekmonkey.local")
                .fullName("Geekmonkey Member")
                .password(passwordEncoder.encode("Member@123"))
                .roles(Set.of(Role.MEMBER))
                .teamIds(Set.of(team.getId()))
                .createdAt(Instant.now())
                .build());

            team.setLeadId(manager.getId());
            team.getMemberIds().addAll(List.of(admin.getId(), manager.getId(), member.getId()));
            teamRepository.save(team);

            Project project = projectRepository.save(Project.builder()
                .name("Courier Claims")
                .description("Claim tracking for courier partners")
                .teamId(team.getId())
                .createdAt(Instant.now())
                .build());

            Sprint sprint = sprintRepository.save(Sprint.builder()
                .name("Sprint 1")
                .goal("Stabilize claim intake")
                .startDate(LocalDate.now().minusDays(3))
                .endDate(LocalDate.now().plusDays(7))
                .teamId(team.getId())
                .status(SprintStatus.ACTIVE)
                .createdAt(Instant.now())
                .build());

            List<Task> tasks = List.of(
                Task.builder().title("Verify inbound claim data").description("Check claim payloads")
                    .teamId(team.getId()).projectId(project.getId()).sprintId(sprint.getId())
                    .assigneeId(member.getId()).reporterId(manager.getId())
                    .status(TaskStatus.TODO).priority(TaskPriority.MEDIUM).type(TaskType.TASK)
                    .estimateMinutes(180).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Task.builder().title("API latency monitoring").description("Set up dashboards")
                    .teamId(team.getId()).projectId(project.getId()).sprintId(sprint.getId())
                    .assigneeId(manager.getId()).reporterId(admin.getId())
                    .status(TaskStatus.IN_PROGRESS).priority(TaskPriority.HIGH).type(TaskType.STORY)
                    .estimateMinutes(240).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Task.builder().title("Blocked: courier SLA policy").description("Need finance approval")
                    .teamId(team.getId()).projectId(project.getId()).sprintId(sprint.getId())
                    .assigneeId(member.getId()).reporterId(manager.getId())
                    .status(TaskStatus.BLOCKED).priority(TaskPriority.URGENT).type(TaskType.BUG)
                    .blockedReason("Awaiting finance approval")
                    .estimateMinutes(120).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Task.builder().title("Review reconciliation report").description("Review Q2 reconciliation")
                    .teamId(team.getId()).projectId(project.getId()).sprintId(sprint.getId())
                    .assigneeId(manager.getId()).reporterId(admin.getId())
                    .status(TaskStatus.REVIEW).priority(TaskPriority.MEDIUM).type(TaskType.TASK)
                    .estimateMinutes(90).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Task.builder().title("Close resolved claims").description("Finalize completed claims")
                    .teamId(team.getId()).projectId(project.getId()).sprintId(sprint.getId())
                    .assigneeId(member.getId()).reporterId(manager.getId())
                    .status(TaskStatus.DONE).priority(TaskPriority.LOW).type(TaskType.TASK)
                    .estimateMinutes(60).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Task.builder().title("Backlog: automate SLA checks").description("Automation ideas")
                    .teamId(team.getId()).projectId(project.getId())
                    .assigneeId(manager.getId()).reporterId(admin.getId())
                    .status(TaskStatus.TODO).priority(TaskPriority.HIGH).type(TaskType.STORY)
                    .estimateMinutes(300).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Task.builder().title("Backlog: claim documentation" ).description("Improve docs")
                    .teamId(team.getId()).projectId(project.getId())
                    .assigneeId(member.getId()).reporterId(manager.getId())
                    .status(TaskStatus.TODO).priority(TaskPriority.LOW).type(TaskType.TASK)
                    .estimateMinutes(120).createdAt(Instant.now()).updatedAt(Instant.now()).build(),
                Task.builder().title("Backlog: bug triage" ).description("Weekly triage")
                    .teamId(team.getId()).projectId(project.getId())
                    .assigneeId(manager.getId()).reporterId(admin.getId())
                    .status(TaskStatus.TODO).priority(TaskPriority.MEDIUM).type(TaskType.BUG)
                    .estimateMinutes(60).createdAt(Instant.now()).updatedAt(Instant.now()).build()
            );

            taskRepository.saveAll(tasks);
        };
    }
}
