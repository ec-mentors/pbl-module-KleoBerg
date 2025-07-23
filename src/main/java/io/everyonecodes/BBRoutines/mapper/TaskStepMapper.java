package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.TaskStepDto;
import io.everyonecodes.BBRoutines.model.TaskStep;
import org.springframework.stereotype.Component;

@Component
public class TaskStepMapper {

    private final StepMapper stepMapper;

    public TaskStepMapper(StepMapper stepMapper) {
        this.stepMapper = stepMapper;
    }

    public TaskStepDto toDto(TaskStep taskStep) {
        if (taskStep == null) {
            return null;
        }
        return TaskStepDto.builder()
                .sequenceOrder(taskStep.getSequenceOrder())
                .expectedDurationSeconds(taskStep.getExpectedDurationSeconds())
                .step(stepMapper.toDto(taskStep.getStep()))
                .build();
    }

    public TaskStep toEntity(TaskStepDto dto) {
        if (dto == null) {
            return null;
        }
        return TaskStep.builder()
                .sequenceOrder(dto.getSequenceOrder())
                .expectedDurationSeconds(dto.getExpectedDurationSeconds())
                .step(stepMapper.toEntity(dto.getStep())) // Creates a transient Step entity
                .build();
    }
}