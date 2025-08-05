package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.TaskDto;
import io.everyonecodes.BBRoutines.model.Task;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TaskMapper {

    private final TaskStepMapper taskStepMapper;

    public TaskMapper(TaskStepMapper taskStepMapper) {
        this.taskStepMapper = taskStepMapper;
    }

    public TaskDto toDto(Task task) {
        if (task == null) {
            return null;
        }
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .iconUrl(task.getIconUrl())
                .audioCueUrl(task.getAudioCueUrl())
                .taskSteps(
                        task.getTaskSteps().stream()
                                .map(taskStepMapper::toDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

}