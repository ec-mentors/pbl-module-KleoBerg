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
public class TaskExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoutineExecution routineExecution;

    @ManyToOne(fetch = FetchType.LAZY)
    private RoutineTask routineTask;

    @Column(nullable = false)
    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;
    private Integer actualDurationSeconds;
    private Integer timeAdjustmentApplied;

    @Column(nullable = false)
    private boolean wasCompleted = false;

    @Column(nullable = false)
    private boolean alertTriggered = false;

    @Column(nullable = false)
    private int expectedDurationAtExecutionSeconds;

    @Column(nullable = false)
    private int sequenceOrderAtExecution;

    @OneToMany(mappedBy = "taskExecution", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("actualStartTime ASC")
    @Builder.Default
    private List<StepExecution> stepExecutions = new ArrayList<>();

    // --- Utility Methods for post-creation modification ---
    public void addStepExecution(StepExecution stepExecution) {
        stepExecutions.add(stepExecution);
        stepExecution.setTaskExecution(this);
    }

    public void removeStepExecution(StepExecution stepExecution) {
        stepExecutions.remove(stepExecution);
        stepExecution.setTaskExecution(null);
    }
}