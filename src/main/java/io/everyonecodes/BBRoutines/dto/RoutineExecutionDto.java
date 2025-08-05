package io.everyonecodes.BBRoutines.dto;

import io.everyonecodes.BBRoutines.model.RoutineStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RoutineExecutionDto {
    private Long id;
    private RoutineStatus status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int pausedDurationSeconds;
    private Integer actualTotalDurationSeconds;
    private boolean alertTriggered;
    private RoutineDto routine;
    private List<TaskExecutionDto> taskExecutions;
}