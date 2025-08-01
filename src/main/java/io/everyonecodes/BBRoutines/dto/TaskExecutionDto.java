package io.everyonecodes.BBRoutines.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TaskExecutionDto {
    private Long id;
    private boolean wasCompleted;
    private boolean alertTriggered;
    private int sequenceOrderAtExecution;
    private LocalDateTime actualStartTime;
    private LocalDateTime actualEndTime;
    private int pausedDurationSeconds;
    private Integer actualDurationSeconds;
    private int expectedDurationAtExecutionSeconds;
    private TaskDto task;
    private List<StepExecutionDto> stepExecutions;
}

