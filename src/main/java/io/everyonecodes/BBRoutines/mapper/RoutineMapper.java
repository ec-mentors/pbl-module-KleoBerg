package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.RoutineDto;
import io.everyonecodes.BBRoutines.model.Routine;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoutineMapper {

    private final RoutineTaskMapper routineTaskMapper;

    public RoutineMapper(RoutineTaskMapper routineTaskMapper) {
        this.routineTaskMapper = routineTaskMapper;
    }

    public RoutineDto toDto(Routine routine) {
        if (routine == null) {
            return null;
        }
        return RoutineDto.builder()
                .id(routine.getId())
                .name(routine.getName())
                .description(routine.getDescription())
                .totalExpectedDurationSeconds(routine.getTotalExpectedDurationSeconds())
                .isActive(routine.isActive())
                .routineTasks(
                        routine.getRoutineTasks().stream()
                                .map(routineTaskMapper::toDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public List<RoutineDto> toDtoList(List<Routine> routines) {
        if (routines == null) {
            return null;
        }
        return routines.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}