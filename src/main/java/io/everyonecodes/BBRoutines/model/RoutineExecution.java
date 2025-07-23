package io.everyonecodes.BBRoutines.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoutineExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Routine routine;

    @Column(nullable = false)
    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer actualTotalDurationSeconds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoutineStatus status;

    @Column(nullable = false)
    private boolean alertTriggered = false;

    @OneToMany(mappedBy = "routineExecution", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("actualStartTime ASC")
    @Builder.Default
    private List<TaskExecution> taskExecutions = new ArrayList<>();

    // --- Utility Methods for post-creation modification ---
    public void addTaskExecution(TaskExecution taskExecution) {
        taskExecutions.add(taskExecution);
        taskExecution.setRoutineExecution(this);
    }

    public void removeTaskExecution(TaskExecution taskExecution) {
        taskExecutions.remove(taskExecution);
        taskExecution.setRoutineExecution(null);
    }
}