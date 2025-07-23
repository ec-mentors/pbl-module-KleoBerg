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
public class Routine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private int totalExpectedDurationSeconds;

    @Column(nullable = false)
    private boolean isActive = true;

    @OneToMany(mappedBy = "routine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("sequenceOrder ASC")
    @Builder.Default
    private List<RoutineTask> routineTasks = new ArrayList<>();

    // --- Utility Methods for post-creation modification ---
    public void addRoutineTask(RoutineTask routineTask) {
        routineTasks.add(routineTask);
        routineTask.setRoutine(this);
    }

    public void removeRoutineTask(RoutineTask routineTask) {
        routineTasks.remove(routineTask);
        routineTask.setRoutine(null);
    }
}