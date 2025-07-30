package io.everyonecodes.BBRoutines.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    private String iconUrl;
    private String audioCueUrl;

    @OneToMany(mappedBy = "task", fetch = FetchType.LAZY)
    @Builder.Default
    private List<RoutineTask> routineTaskAssociations = new ArrayList<>();

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sequenceOrder ASC")
    @Builder.Default
    private List<TaskStep> taskSteps = new ArrayList<>();

    // --- Utility Methods for post-creation modification ---
    public void addTaskStep(TaskStep taskStep) {
        taskSteps.add(taskStep);
        taskStep.setTask(this);
    }

    public void removeTaskStep(TaskStep taskStep) {
        taskSteps.remove(taskStep);
        taskStep.setTask(null);
    }
}