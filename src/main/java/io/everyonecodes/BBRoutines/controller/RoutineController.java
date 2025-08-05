package io.everyonecodes.BBRoutines.controller;

import io.everyonecodes.BBRoutines.dto.RoutineDto;
import io.everyonecodes.BBRoutines.service.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routines")
public class RoutineController {

    private final RoutineService routineService;

    public RoutineController(RoutineService routineService) {
        this.routineService = routineService;
    }

    @GetMapping
    public List<RoutineDto> getAllRoutines() {
        return routineService.findAllRoutines();
    }

    @GetMapping("/{id}")
    public RoutineDto getRoutineById(@PathVariable Long id) {
        return routineService.findRoutineById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RoutineDto createRoutine(@RequestBody RoutineDto routineDto) {
        return routineService.createRoutine(routineDto);
    }

    @PutMapping("/{id}")
    public RoutineDto updateRoutine(@PathVariable Long id, @RequestBody RoutineDto routineDto) {
        return routineService.updateRoutine(id, routineDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long id) {
        routineService.deleteRoutineById(id);
        return ResponseEntity.noContent().build();
    }
}