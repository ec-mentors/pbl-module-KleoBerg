package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.StepExecutionDto;
import io.everyonecodes.BBRoutines.model.StepExecution;
import org.springframework.stereotype.Component;

@Component
public class StepExecutionMapper {

    private final StepMapper stepMapper;

    public StepExecutionMapper(StepMapper stepMapper) {
        this.stepMapper = stepMapper;
    }

    public StepExecutionDto toDto(StepExecution exec) {
        if (exec == null) {
            return null;
        }
        return StepExecutionDto.builder()
                .id(exec.getId())
                .wasConfirmed(exec.isWasConfirmed())
                .alertTriggered(exec.isAlertTriggered())
                .sequenceOrderAtExecution(exec.getSequenceOrderAtExecution())
                .actualStartTime(exec.getActualStartTime())
                .actualEndTime(exec.getActualEndTime())
                .pausedDurationSeconds(exec.getPausedDurationSeconds())
                .actualDurationSeconds(exec.getActualDurationSeconds())
                .expectedDurationAtExecutionSeconds(exec.getExpectedDurationAtExecutionSeconds())
                .step(stepMapper.toDto(exec.getTaskStep().getStep()))
                .build();
    }
}