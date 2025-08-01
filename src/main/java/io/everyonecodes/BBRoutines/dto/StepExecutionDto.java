package io.everyonecodes.BBRoutines.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class StepExecutionDto {
    private Long id;
    private boolean wasConfirmed;
    private boolean alertTriggered;
    private int sequenceOrderAtExecution;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private int pausedDurationSeconds;
    private Integer actualDurationSeconds;
    private int expectedDurationAtExecutionSeconds;
    private StepDto step;
}