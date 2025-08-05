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


    @PostMapping
    public RoutineExecutionDto startNewRoutine(@RequestBody StartRoutineRequestDto request) {
        return routineExecutionService.startRoutine(request.getRoutineId());
    }

    @GetMapping("/current")
    public ResponseEntity<RoutineExecutionDto> getCurrentInProgressRoutine() {
        return routineExecutionService.findCurrentInProgressRoutine()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public RoutineExecutionDto getExecutionState(@PathVariable Long id) {
        return routineExecutionService.findActiveRoutineState(id);
    }

    @PostMapping("/{id}/confirm-step")
    public RoutineExecutionDto confirmStep(@PathVariable Long id) {
        return routineExecutionService.confirmCurrentStep(id);
    }


    @PostMapping("/{id}/pause")
    public RoutineExecutionDto pauseRoutine(@PathVariable Long id) {
        return routineExecutionService.pauseRoutine(id);
    }

    @PostMapping("/{id}/resume")
    public RoutineExecutionDto resumeRoutine(@PathVariable Long id) {
        return routineExecutionService.resumeRoutine(id);
    }
}