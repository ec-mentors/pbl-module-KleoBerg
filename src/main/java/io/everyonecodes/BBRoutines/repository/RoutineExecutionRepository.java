package io.everyonecodes.BBRoutines.repository;

import io.everyonecodes.BBRoutines.model.RoutineExecution;
import io.everyonecodes.BBRoutines.model.RoutineStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineExecutionRepository extends JpaRepository<RoutineExecution, Long> {

    Optional<RoutineExecution> findFirstByStatusInOrderByStartTimeDesc(List<RoutineStatus> statuses);
}