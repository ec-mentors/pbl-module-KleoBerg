package io.everyonecodes.BBRoutines.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoutineDto {
    private Long id;
    private String name;
    private String description;
    private int totalExpectedDurationSeconds;
    private boolean isActive;
    @Singular
    private List<RoutineTaskDto> routineTasks;
}