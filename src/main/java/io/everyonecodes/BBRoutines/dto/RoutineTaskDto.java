package io.everyonecodes.BBRoutines.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineTaskDto {
    private int sequenceOrder;
    private int expectedDurationSeconds;
    private TaskDto task;
}