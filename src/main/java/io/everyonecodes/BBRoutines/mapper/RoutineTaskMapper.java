package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.RoutineTaskDto;
import io.everyonecodes.BBRoutines.model.RoutineTask;
import org.springframework.stereotype.Component;

@Component
public class RoutineTaskMapper {

    private final TaskMapper taskMapper;

    public RoutineTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    public RoutineTaskDto toDto(RoutineTask routineTask) {
        if (routineTask == null) {
            return null;
        }
        return RoutineTaskDto.builder()
                .sequenceOrder(routineTask.getSequenceOrder())
                .expectedDurationSeconds(routineTask.getExpectedDurationSeconds())
                .task(taskMapper.toDto(routineTask.getTask()))
                .build();
    }

}