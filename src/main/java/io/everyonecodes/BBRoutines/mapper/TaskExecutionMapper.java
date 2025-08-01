package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.TaskExecutionDto;
import io.everyonecodes.BBRoutines.model.TaskExecution;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskExecutionMapper {

    private final TaskMapper taskMapper;
    private final StepExecutionMapper stepExecutionMapper;

    public TaskExecutionMapper(TaskMapper taskMapper, StepExecutionMapper stepExecutionMapper) {
        this.taskMapper = taskMapper;
        this.stepExecutionMapper = stepExecutionMapper;
    }

    public TaskExecutionDto toDto(TaskExecution exec) {
        if (exec == null) {
            return null;
        }
        return TaskExecutionDto.builder()
                .id(exec.getId())
                .wasCompleted(exec.isWasCompleted())
                .alertTriggered(exec.isAlertTriggered())
                .sequenceOrderAtExecution(exec.getSequenceOrderAtExecution())
                .actualStartTime(exec.getActualStartTime())
                .actualEndTime(exec.getActualEndTime())
                .pausedDurationSeconds(exec.getPausedDurationSeconds())
                .actualDurationSeconds(exec.getActualDurationSeconds())
                .expectedDurationAtExecutionSeconds(exec.getExpectedDurationAtExecutionSeconds())
                .task(taskMapper.toDto(exec.getRoutineTask().getTask()))
                .stepExecutions(
                        exec.getStepExecutions().stream()
                                .map(stepExecutionMapper::toDto)
                                .collect(Collectors.toList())
                )
                .build();
    }
}