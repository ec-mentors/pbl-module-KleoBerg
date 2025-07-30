package io.everyonecodes.BBRoutines.service;

import io.everyonecodes.BBRoutines.dto.StepDto;
import io.everyonecodes.BBRoutines.mapper.StepMapper;
import io.everyonecodes.BBRoutines.model.Step;
import io.everyonecodes.BBRoutines.repository.StepRepository;
import io.everyonecodes.BBRoutines.repository.TaskStepRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StepService {

    private final StepRepository stepRepository;
    private final TaskStepRepository taskStepRepository;
    private final StepMapper stepMapper;

    public StepService(StepRepository stepRepository, TaskStepRepository taskStepRepository, StepMapper stepMapper) {
        this.stepRepository = stepRepository;
        this.taskStepRepository = taskStepRepository;
        this.stepMapper = stepMapper;
    }

    public List<StepDto> findAllSteps() {
        return stepRepository.findAll().stream()
                .map(stepMapper::toDto)
                .collect(Collectors.toList());
    }

    public StepDto findStepById(Long id) {
        return stepRepository.findById(id)
                .map(stepMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Step not found with ID: " + id));
    }

    public StepDto createStep(StepDto stepDto) {
        stepDto.setId(null);
        Step newStep = buildStepFromDto(stepDto);
        Step savedStep = stepRepository.save(newStep);
        return stepMapper.toDto(savedStep);
    }

    public StepDto updateStep(Long id, StepDto stepDto) {
        if (!stepRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot update. Step not found with ID: " + id);
        }

        stepDto.setId(id);
        Step updatedStep = buildStepFromDto(stepDto);
        Step savedStep = stepRepository.save(updatedStep);
        return stepMapper.toDto(savedStep);
    }

    public void deleteStepById(Long id) {
        if (taskStepRepository.existsByStepId(id)) {
            throw new IllegalStateException("Cannot delete Step with ID " + id + ". It is currently in use by one or more tasks.");
        }
        if (!stepRepository.existsById(id)) {
            throw new IllegalArgumentException("Cannot delete. Step not found with ID: " + id);
        }
        stepRepository.deleteById(id);
    }


    private Step buildStepFromDto(StepDto stepDto) {
        return Step.builder()
                .id(stepDto.getId())
                .name(stepDto.getName())
                .description(stepDto.getDescription())
                .iconUrl(stepDto.getIconUrl())
                .audioCueUrl(stepDto.getAudioCueUrl())
                .build();
    }
}