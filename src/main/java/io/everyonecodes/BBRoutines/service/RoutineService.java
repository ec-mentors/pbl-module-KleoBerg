package io.everyonecodes.BBRoutines.service;

import io.everyonecodes.BBRoutines.dto.RoutineDto;
import io.everyonecodes.BBRoutines.dto.RoutineTaskDto;
import io.everyonecodes.BBRoutines.dto.TaskStepDto;
import io.everyonecodes.BBRoutines.mapper.RoutineMapper;
import io.everyonecodes.BBRoutines.model.*;
import io.everyonecodes.BBRoutines.repository.RoutineRepository;
import io.everyonecodes.BBRoutines.repository.StepRepository;
import io.everyonecodes.BBRoutines.repository.TaskRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final TaskRepository taskRepository;
    private final StepRepository stepRepository;
    private final RoutineMapper routineMapper;

    public RoutineService(RoutineRepository routineRepository, TaskRepository taskRepository, StepRepository stepRepository, RoutineMapper routineMapper) {
        this.routineRepository = routineRepository;
        this.taskRepository = taskRepository;
        this.stepRepository = stepRepository;
        this.routineMapper = routineMapper;
    }


    public List<RoutineDto> findAllRoutines() {
        return routineRepository.findAll().stream()
                .map(routineMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoutineDto findRoutineById(Long id) {
        return routineRepository.findById(id)
                .map(routineMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found with ID: " + id));
    }

    public RoutineDto createRoutine(RoutineDto routineDto) {
        routineDto.setId(null);
        Routine newRoutine = buildRoutineFromDto(routineDto);
        Routine savedRoutine = routineRepository.save(newRoutine);
        return routineMapper.toDto(savedRoutine);
    }

    public RoutineDto updateRoutine(Long id, RoutineDto routineDto) {
        if (routineRepository.existsById(id)) {
                throw new IllegalArgumentException("Cannot update. Routine not found with ID: " + id);
        }
        routineDto.setId(id);
        Routine updatedRoutine = buildRoutineFromDto(routineDto);
        Routine savedRoutine = routineRepository.save(updatedRoutine);
        return routineMapper.toDto(savedRoutine);
    }

    public void deleteRoutineById(Long id) {
        if (!routineRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete. Routine not found with ID: " + id);
        }
        routineRepository.deleteById(id);
    }


    private Routine buildRoutineFromDto(RoutineDto routineDto) {
        Routine routine = Routine.builder()
                .id(routineDto.getId())
                .name(routineDto.getName())
                .description(routineDto.getDescription())
                .totalExpectedDurationSeconds(routineDto.getTotalExpectedDurationSeconds())
                .isActive(routineDto.getIsActive())
                .build();


        if (routineDto.getRoutineTasks() != null) {
            for (RoutineTaskDto routineTaskDto : routineDto.getRoutineTasks()) {
                Long taskId = routineTaskDto.getTask().getId();
                Task existingTask = taskRepository.findById(taskId)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid data. Task not found with ID: " + taskId));


                RoutineTask newRoutineTask = RoutineTask.builder()
                        .sequenceOrder(routineTaskDto.getSequenceOrder())
                        .expectedDurationSeconds(routineTaskDto.getExpectedDurationSeconds())
                        .task(existingTask)
                        .build();

                if (routineTaskDto.getTask().getTaskSteps() != null) {
                    for (TaskStepDto taskStepDto : routineTaskDto.getTask().getTaskSteps()) {
                        Long stepId = taskStepDto.getStep().getId();
                        Step existingStep = stepRepository.findById(stepId)
                                .orElseThrow(() -> new IllegalArgumentException("Invalid data. Step not found with ID: " + stepId));

                        TaskStep newTaskStep = TaskStep.builder()
                                .sequenceOrder(taskStepDto.getSequenceOrder())
                                .expectedDurationSeconds(taskStepDto.getExpectedDurationSeconds())
                                .step(existingStep)
                                .build();

                        existingTask.addTaskStep(newTaskStep);
                    }
                }

                routine.addRoutineTask(newRoutineTask);
            }
        }
        return routine;
    }


}