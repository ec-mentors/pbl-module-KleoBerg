package io.everyonecodes.BBRoutines.controller;

import io.everyonecodes.BBRoutines.dto.StepDto;
import io.everyonecodes.BBRoutines.service.StepService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/steps")
public class StepController {

    private final StepService stepService;

    public StepController(StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping
    public List<StepDto> getAllSteps() {
        return stepService.findAllSteps();
    }

    @GetMapping("/{id}")
    public StepDto getStepById(@PathVariable Long id) {
        return stepService.findStepById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StepDto createNewStep(@RequestBody StepDto stepDto) {
        return stepService.createStep(stepDto);
    }

    @PutMapping("/{id}")
    public StepDto updateStep(@PathVariable Long id, @RequestBody StepDto stepDto) {
        return stepService.updateStep(id, stepDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable Long id) {
        stepService.deleteStepById(id);
        return ResponseEntity.noContent().build();
    }
}