package io.everyonecodes.BBRoutines.repository;

import io.everyonecodes.BBRoutines.model.TaskStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskStepRepository extends JpaRepository<TaskStep, Long> {
    boolean existsByStepId(Long stepId);
}