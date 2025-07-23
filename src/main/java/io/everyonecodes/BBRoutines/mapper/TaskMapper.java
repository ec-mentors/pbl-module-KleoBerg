package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.TaskDto;
import io.everyonecodes.BBRoutines.model.Task;
import io.everyonecodes.BBRoutines.model.TaskStep;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public Task toEntity(TaskDto dto) {
        if (dto == null) { return null; }
        Task task = Task.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .iconUrl(dto.getIconUrl())
                .audioCueUrl(dto.getAudioCueUrl())
                .build();

        // Map and link the child TaskSteps
        List<TaskStep> taskSteps = dto.getTaskSteps().stream()
                .map(taskStepMapper::toEntity)
                .collect(Collectors.toList());
        taskSteps.forEach(task::addTaskStep); // Use the utility method to set both sides!
        return task;
    }
}