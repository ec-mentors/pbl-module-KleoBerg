package io.everyonecodes.BBRoutines.mapper;

import io.everyonecodes.BBRoutines.dto.StepDto;
import io.everyonecodes.BBRoutines.model.Step;
import org.springframework.stereotype.Component;

@Component
public class StepMapper {

    public StepDto toDto(Step step) {
        if (step == null) {
            return null;
        }
        return StepDto.builder()
                .id(step.getId())
                .name(step.getName())
                .description(step.getDescription())
                .iconUrl(step.getIconUrl())
                .audioCueUrl(step.getAudioCueUrl())
                .build();
    }
}