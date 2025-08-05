package io.everyonecodes.BBRoutines.service;

import io.everyonecodes.BBRoutines.dto.RoutineExecutionDto;
import io.everyonecodes.BBRoutines.mapper.RoutineExecutionMapper;
import io.everyonecodes.BBRoutines.model.*;
import io.everyonecodes.BBRoutines.repository.RoutineExecutionRepository;
import io.everyonecodes.BBRoutines.repository.RoutineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoutineExecutionService {

    private final RoutineRepository routineRepository;
    private final RoutineExecutionRepository routineExecutionRepository;
    private final RoutineExecutionMapper routineExecutionMapper;

    public RoutineExecutionService(RoutineRepository routineRepository,
                                   RoutineExecutionRepository routineExecutionRepository,
                                   RoutineExecutionMapper routineExecutionMapper) {
        this.routineRepository = routineRepository;
        this.routineExecutionRepository = routineExecutionRepository;
        this.routineExecutionMapper = routineExecutionMapper;
    }


    public List<RoutineExecutionDto> findAllExecutions() {
        List<RoutineExecution> executions = routineExecutionRepository.findAll();
        return executions.stream()
                .map(routineExecutionMapper::toDto)
                .collect(Collectors.toList());
    }

    public RoutineExecutionDto startRoutine(Long routineId) {
        findCurrentInProgressRoutineEntity().ifPresent(oldExecution -> {
            oldExecution.setStatus(RoutineStatus.ABANDONED);
            oldExecution.setEndTime(LocalDateTime.now());
            routineExecutionRepository.save(oldExecution);
        });

        Routine routineDefinition = routineRepository.findById(routineId)
                .orElseThrow(() -> new IllegalArgumentException("Routine definition not found with ID: " + routineId));

        RoutineExecution routineExecution = createExecutionPlanFromDefinition(routineDefinition);

        findNextPendingStep(routineExecution).ifPresent(firstStep -> {
            firstStep.setActualStartTime(LocalDateTime.now());
            firstStep.getTaskExecution().setActualStartTime(LocalDateTime.now());
        });

        RoutineExecution savedExecution = routineExecutionRepository.save(routineExecution);
        return routineExecutionMapper.toDto(savedExecution);
    }

    public RoutineExecutionDto confirmCurrentStep(Long executionId) {
        RoutineExecution execution = findExecutionById(executionId);
        if (execution.getStatus() != RoutineStatus.STARTED) {
            throw new IllegalStateException("Can only confirm steps on a routine that is currently started.");
        }

        StepExecution currentStep = findNextPendingStep(execution)
                .orElseThrow(() -> new IllegalStateException("No pending steps to confirm in this routine."));

        currentStep.setWasConfirmed(true);
        currentStep.setActualEndTime(LocalDateTime.now());
        if (currentStep.getActualStartTime() != null) {
            long totalSeconds = Duration.between(currentStep.getActualStartTime(), currentStep.getActualEndTime()).getSeconds();
            currentStep.setActualDurationSeconds((int) totalSeconds - currentStep.getPausedDurationSeconds());
        }

        updateParentCompletionStatus(currentStep.getTaskExecution());


        findNextPendingStep(execution).ifPresent(nextStep -> {
            nextStep.setActualStartTime(LocalDateTime.now());
            if (nextStep.getTaskExecution().getActualStartTime() == null) {
                nextStep.getTaskExecution().setActualStartTime(LocalDateTime.now());
            }
        });

        return routineExecutionMapper.toDto(execution);
    }

    public RoutineExecutionDto pauseRoutine(Long executionId) {
        RoutineExecution execution = findExecutionById(executionId);
        if (execution.getStatus() != RoutineStatus.STARTED) {
            throw new IllegalStateException("Can only pause a routine that is currently started.");
        }
        execution.setStatus(RoutineStatus.PAUSED);
        execution.setPauseInitiatedAt(LocalDateTime.now()); // Record when the pause began
        return routineExecutionMapper.toDto(execution);
    }

    public RoutineExecutionDto resumeRoutine(Long executionId) {
        RoutineExecution execution = findExecutionById(executionId);
        if (execution.getStatus() != RoutineStatus.PAUSED || execution.getPauseInitiatedAt() == null) {
            throw new IllegalStateException("Can only resume a routine that is currently paused.");
        }

        long pauseSeconds = Duration.between(execution.getPauseInitiatedAt(), LocalDateTime.now()).getSeconds();

        findNextPendingStep(execution).ifPresent(currentStep -> {
            currentStep.setPausedDurationSeconds(currentStep.getPausedDurationSeconds() + (int) pauseSeconds);
            currentStep.getTaskExecution().setPausedDurationSeconds(currentStep.getTaskExecution().getPausedDurationSeconds() + (int) pauseSeconds);
            execution.setPausedDurationSeconds(execution.getPausedDurationSeconds() + (int) pauseSeconds);
        });

        execution.setStatus(RoutineStatus.STARTED);
        execution.setPauseInitiatedAt(null); // Clear the pause start time
        return routineExecutionMapper.toDto(execution);
    }

    public Optional<RoutineExecutionDto> findCurrentInProgressRoutine() {
        return findCurrentInProgressRoutineEntity().map(routineExecutionMapper::toDto);
    }

    public RoutineExecutionDto findActiveRoutineState(Long executionId) {
        return routineExecutionRepository.findById(executionId)
                .map(routineExecutionMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Routine execution not found with ID: " + executionId));
    }





    private RoutineExecution createExecutionPlanFromDefinition(Routine routineDefinition) {
        RoutineExecution execution = RoutineExecution.builder()
                .routine(routineDefinition)
                .status(RoutineStatus.STARTED)
                .startTime(LocalDateTime.now())
                .build();

        for (RoutineTask routineTaskDef : routineDefinition.getRoutineTasks()) {
            TaskExecution taskExec = TaskExecution.builder()
                    .routineTask(routineTaskDef)
                    .expectedDurationAtExecutionSeconds(routineTaskDef.getExpectedDurationSeconds())
                    .sequenceOrderAtExecution(routineTaskDef.getSequenceOrder())
                    .build();

            for (TaskStep taskStepDef : routineTaskDef.getTask().getTaskSteps()) {
                StepExecution stepExec = StepExecution.builder()
                        .taskStep(taskStepDef)
                        .expectedDurationAtExecutionSeconds(taskStepDef.getExpectedDurationSeconds())
                        .sequenceOrderAtExecution(taskStepDef.getSequenceOrder())
                        .build();
                taskExec.addStepExecution(stepExec);
            }
            execution.addTaskExecution(taskExec);
        }
        return execution;
    }

    private void updateParentCompletionStatus(TaskExecution taskExecution) {
        boolean allStepsConfirmed = taskExecution.getStepExecutions().stream().allMatch(StepExecution::isWasConfirmed);

        if (allStepsConfirmed) {
            taskExecution.setWasCompleted(true);
            taskExecution.setActualEndTime(LocalDateTime.now());
            if (taskExecution.getActualStartTime() != null) {
                long totalSeconds = Duration.between(taskExecution.getActualStartTime(), taskExecution.getActualEndTime()).getSeconds();
                taskExecution.setActualDurationSeconds((int) totalSeconds - taskExecution.getPausedDurationSeconds());
            }

            RoutineExecution routineExecution = taskExecution.getRoutineExecution();
            boolean allTasksCompleted = routineExecution.getTaskExecutions().stream().allMatch(TaskExecution::isWasCompleted);

            if (allTasksCompleted) {
                routineExecution.setStatus(RoutineStatus.COMPLETED);
                routineExecution.setEndTime(LocalDateTime.now());
                long totalSeconds = Duration.between(routineExecution.getStartTime(), routineExecution.getEndTime()).getSeconds();
                routineExecution.setActualTotalDurationSeconds((int) totalSeconds - routineExecution.getPausedDurationSeconds());
            }
        }
    }

    private Optional<RoutineExecution> findCurrentInProgressRoutineEntity() {
        List<RoutineStatus> inProgressStatuses = List.of(RoutineStatus.STARTED, RoutineStatus.PAUSED);
        return routineExecutionRepository.findFirstByStatusInOrderByStartTimeDesc(inProgressStatuses);
    }

    private RoutineExecution findExecutionById(Long executionId) {
        return routineExecutionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Routine execution not found with ID: " + executionId));
    }

    private Optional<StepExecution> findNextPendingStep(RoutineExecution execution) {
        return execution.getTaskExecutions().stream()
                .filter(task -> !task.isWasCompleted())
                .flatMap(task -> task.getStepExecutions().stream())
                .filter(step -> !step.isWasConfirmed())
                .findFirst();
    }
}