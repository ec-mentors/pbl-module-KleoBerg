package io.everyonecodes.BBRoutines.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDto {
    private Long id;
    private String name;
    private String description;
    private String iconUrl;
    private String audioCueUrl;
    @Singular
    private List<TaskStepDto> taskSteps;
}