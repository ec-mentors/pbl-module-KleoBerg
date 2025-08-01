package io.everyonecodes.BBRoutines.controller;

import io.everyonecodes.BBRoutines.dto.RoutineExecutionDto;
import io.everyonecodes.BBRoutines.dto.StartRoutineRequestDto;
import io.everyonecodes.BBRoutines.service.RoutineExecutionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/executions")
public class RoutineExecutionController {

    private final RoutineExecutionService routineExecutionService;

    public RoutineExecutionController(RoutineExecutionService routineExecutionService) {
        this.routineExecutionService = routineExecutionService;
    }

    @GetMapping
    public List<RoutineExecutionDto> getAllExecutions() {
        return routineExecutionService.findAllExecutions();
    }

    /**
     * Starts a new execution of a defined routine.
     * The client must provide the ID of the routine definition in the request body.
     */
    @PostMapping
    public RoutineExecutionDto startNewRoutine(@RequestBody StartRoutineRequestDto request) {
        return routineExecutionService.startRoutine(request.getRoutineId());
    }

    /**
     * Checks if there is a routine currently in progress (STARTED or PAUSED).
     * Useful for a frontend to decide if it should show a "Resume Routine" screen upon loading.
     */
    @GetMapping("/current")
    public ResponseEntity<RoutineExecutionDto> getCurrentInProgressRoutine() {
        return routineExecutionService.findCurrentInProgressRoutine()
                .map(ResponseEntity::ok) // If present, wrap it in a 200 OK response
                .orElse(ResponseEntity.noContent().build()); // If not present, return 204 No Content
    }

    /**
     * Gets the full state of a specific, ongoing routine execution.
     */
    @GetMapping("/{id}")
    public RoutineExecutionDto getExecutionState(@PathVariable Long id) {
        return routineExecutionService.findActiveRoutineState(id);
    }

    /**
     * Confirms the completion of the current pending step in a routine.
     * This is the "high-five" action that advances the routine.
     */
    @PostMapping("/{id}/confirm-step")
    public RoutineExecutionDto confirmStep(@PathVariable Long id) {
        return routineExecutionService.confirmCurrentStep(id);
    }

    /**
     * Pauses a currently running routine.
     */
    @PostMapping("/{id}/pause")
    public RoutineExecutionDto pauseRoutine(@PathVariable Long id) {
        return routineExecutionService.pauseRoutine(id);
    }

    /**
     * Resumes a currently paused routine.
     */
    @PostMapping("/{id}/resume")
    public RoutineExecutionDto resumeRoutine(@PathVariable Long id) {
        return routineExecutionService.resumeRoutine(id);
    }
}