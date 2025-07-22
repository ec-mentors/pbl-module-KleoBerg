package io.everyonecodes.BBRoutines.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoutineTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Routine routine;

    @ManyToOne(fetch = FetchType.LAZY)
    private Task task;

    @Column(nullable = false)
    private int sequenceOrder;

    @Column(nullable = false)
    private int expectedDurationSeconds;
}