package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.RoutineExecutionDto;
import io.everyonecodes.BBRoutines.model.RoutineExecution;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoutineExecutionMapper {

    private final RoutineMapper routineMapper;
    private final TaskExecutionMapper taskExecutionMapper;

    public RoutineExecutionMapper(RoutineMapper routineMapper, TaskExecutionMapper taskExecutionMapper) {
        this.routineMapper = routineMapper;
        this.taskExecutionMapper = taskExecutionMapper;
    }

    public RoutineExecutionDto toDto(RoutineExecution exec) {
        if (exec == null) {
            return null;
        }
        return RoutineExecutionDto.builder()
                .id(exec.getId())
                .status(exec.getStatus())
                .startTime(exec.getStartTime())
                .endTime(exec.getEndTime())
                .pausedDurationSeconds(exec.getPausedDurationSeconds())
                .actualTotalDurationSeconds(exec.getActualTotalDurationSeconds())
                .alertTriggered(exec.isAlertTriggered())
                .routine(routineMapper.toDto(exec.getRoutine()))
                .taskExecutions(
                        exec.getTaskExecutions().stream()
                                .map(taskExecutionMapper::toDto)
                                .collect(Collectors.toList())
                )
                .build();
    }
}