package io.everyonecodes.BBRoutines.service;

import io.everyonecodes.BBRoutines.dto.RoutineDto;
import io.everyonecodes.BBRoutines.mapper.RoutineMapper;
import io.everyonecodes.BBRoutines.repository.RoutineRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final RoutineMapper routineMapper;

    public RoutineService(RoutineRepository routineRepository, RoutineMapper routineMapper) {
        this.routineRepository = routineRepository;
        this.routineMapper = routineMapper;
    }


    public List<RoutineDto> findAllRoutines() {
        var routines = routineRepository.findAll();
        return routineMapper.toDtoList(routines);
    }

    public RoutineDto findRoutineById(Long id) {

        return routineRepository.findById(id)
                .map(routineMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found with ID: " + id));
    }

    public RoutineDto createRoutine(RoutineDto routineDto) {
        routineDto.setId(null);
        var routineToSave = routineMapper.toEntity(routineDto);

        var savedRoutine = routineRepository.save(routineToSave);

        // 3. Map the saved entity (which now has an ID) back to a DTO and return it.
        return routineMapper.toDto(savedRoutine);
    }

    // You would continue by creating methods for update and delete as well.
}