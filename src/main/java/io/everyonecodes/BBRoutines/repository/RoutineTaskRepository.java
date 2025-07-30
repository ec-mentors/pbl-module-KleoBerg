package io.everyonecodes.BBRoutines.repository;

import io.everyonecodes.BBRoutines.model.RoutineTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineTaskRepository extends JpaRepository<RoutineTask, Long> {
    boolean existsByTaskId(Long taskId);
}