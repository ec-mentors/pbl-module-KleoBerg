package io.everyonecodes.BBRoutines.service;

import io.everyonecodes.BBRoutines.dto.TaskDto;
import io.everyonecodes.BBRoutines.dto.TaskStepDto;
import io.everyonecodes.BBRoutines.mapper.TaskMapper;
import io.everyonecodes.BBRoutines.model.Step;
import io.everyonecodes.BBRoutines.model.Task;
import io.everyonecodes.BBRoutines.model.TaskStep;
import io.everyonecodes.BBRoutines.repository.RoutineTaskRepository;
import io.everyonecodes.BBRoutines.repository.StepRepository;
import io.everyonecodes.BBRoutines.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final StepRepository stepRepository;
    private final RoutineTaskRepository routineTaskRepository; // For deletion check
    private final TaskMapper taskMapper;

    public TaskService(TaskRepository taskRepository, StepRepository stepRepository, RoutineTaskRepository routineTaskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.stepRepository = stepRepository;
        this.routineTaskRepository = routineTaskRepository;
        this.taskMapper = taskMapper;
    }

    public List<TaskDto> findAllTasks() {
        return taskRepository.findAll().stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDto findTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Task not found with ID: " + id));
    }

    public TaskDto createTask(TaskDto taskDto) {
        taskDto.setId(null);
        Task newTask = buildTaskFromDto(taskDto);
        Task savedTask = taskRepository.save(newTask);
        return taskMapper.toDto(savedTask);
    }

    public TaskDto updateTask(Long id, TaskDto taskDto) {
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot update. Task not found with ID: " + id);
        }

        taskDto.setId(id);
        Task updatedTask = buildTaskFromDto(taskDto);
        Task savedTask = taskRepository.save(updatedTask);
        return taskMapper.toDto(savedTask);
    }

    public void deleteTaskById(Long id) {
        if (routineTaskRepository.existsByTaskId(id)) {
            throw new IllegalStateException("Cannot delete Task with ID " + id + ". It is currently in use by one or more routines.");
        }
        if (!taskRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete. Task not found with ID: " + id);
        }
        taskRepository.deleteById(id);
    }

    private Task buildTaskFromDto(TaskDto taskDto) {
        Task task = Task.builder()
                .id(taskDto.getId())
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .iconUrl(taskDto.getIconUrl())
                .audioCueUrl(taskDto.getAudioCueUrl())
                .build();

        if (taskDto.getTaskSteps() != null) {
            for (TaskStepDto taskStepDto : taskDto.getTaskSteps()) {
                Long stepId = taskStepDto.getStep().getId();
                Step existingStep = stepRepository.findById(stepId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid data. Step not found with ID: " + stepId));

                TaskStep newTaskStep = TaskStep.builder()
                        .sequenceOrder(taskStepDto.getSequenceOrder())
                        .expectedDurationSeconds(taskStepDto.getExpectedDurationSeconds())
                        .step(existingStep)
                        .build();

                task.addTaskStep(newTaskStep);
            }
        }
        return task;
    }
}