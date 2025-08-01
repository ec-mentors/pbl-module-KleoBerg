package io.everyonecodes.BBRoutines.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StepExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskExecution taskExecution;

    @ManyToOne(fetch = FetchType.LAZY)
    private TaskStep taskStep;

    private LocalDateTime actualStartTime;

    private LocalDateTime actualEndTime;
    private int pausedDurationSeconds = 0;
    private Integer actualDurationSeconds;
    //add time adjustement

    @Column(nullable = false)
    private boolean wasConfirmed = false;

    @Column(nullable = false)
    private boolean alertTriggered = false;

    @Column(nullable = false)
    private int expectedDurationAtExecutionSeconds;

    @Column(nullable = false)
    private int sequenceOrderAtExecution;
}